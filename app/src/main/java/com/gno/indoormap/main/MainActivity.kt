package com.gno.indoormap.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.gno.indoormap.R
import com.gno.indoormap.databinding.ActivityMainBinding
import com.gno.indoormap.di.component.ActivityComponent
import com.gno.indoormap.di.component.DaggerActivityComponent
import com.gno.indoormap.di.module.ActivityModule
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    lateinit var activityComponent: ActivityComponent
    private lateinit var activityMainBinding: ActivityMainBinding

    @Inject
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //data binding
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //dagger
        activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .applicationComponent((application as MainApplication).applicationComponent)
            .build()
        activityComponent.inject(this)

        //toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_search) navController.navigate(R.id.searchFragment)
//       NavigationUI.onNavDestinationSelected(item, navController);
//        return super.onOptionsItemSelected(item);
        return true

    }
}