package com.gno.indoormap.custommapview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.gno.indoormap.R
import com.gno.indoormap.model.Point
import com.onlylemi.mapview.library.layer.MapBaseLayer

class RoomLayer(private val mapPicture: MapPicture, private val figurePoints: List<Point>, visible: Boolean) :
    MapBaseLayer(
        mapPicture
    ) {

    lateinit var listener: RoomIsClickListener
    var context: Context? = null

    init {
        isVisible = visible
    }

    override fun onTouch(motionEvent: MotionEvent) {

        val polygonBuilder = PolygonFix.PolygonBuilder()

        for (point in figurePoints) {
            polygonBuilder.addVertex(
                com.snatik.polygon.Point(
                    point.x.toDouble(),
                    point.y.toDouble()
                )
            )
        }
        val polygon = polygonBuilder.build()
        val goal = mapView.convertMapXYToScreenXY(motionEvent.x, motionEvent.y)
        val contains = polygon.contains(
            com.snatik.polygon.Point(
                goal[0].toDouble(), goal[1].toDouble()
            )
        )
        if (contains) {
            if (mapPicture.activeRoomLayerSsInitialized()) {
                mapPicture.activeRoomLayer.isVisible = false
            }
            isVisible = true
            mapPicture.activeRoomLayer = this
            listener.roomIsClick(isVisible)
            mapPicture.refresh()
        }
    }


    override fun draw(canvas: Canvas, matrix: Matrix, v: Float, v1: Float) {
        if (isVisible) {
            val colorDarkGrey = ContextCompat.getColor(mapView.context, R.color.colorDarkGrey)
            val colorBlack = ContextCompat.getColor(mapView.context, R.color.colorBlack)
            var paint = Paint()
            paint.style = Paint.Style.FILL
            paint.color = colorDarkGrey
            paint.strokeWidth = 1f
            val path = Path()
            canvas.save()
            for (i in figurePoints.indices) {
                val goal = floatArrayOf(figurePoints[i].x.toFloat(), figurePoints[i].y.toFloat())
                matrix.mapPoints(goal)
                if (i == 0) {
                    path.moveTo(goal[0], goal[1])
                } else {
                    path.lineTo(goal[0], goal[1])
                }
            }
            canvas.drawPath(path, paint)
            paint = Paint()
            paint.style = Paint.Style.STROKE
            paint.color = colorBlack
            paint.strokeWidth = 1f
            canvas.drawPath(path, paint)
            canvas.restore()
        }
    }

    interface RoomIsClickListener {
        fun roomIsClick(visiable: Boolean)
    }

}