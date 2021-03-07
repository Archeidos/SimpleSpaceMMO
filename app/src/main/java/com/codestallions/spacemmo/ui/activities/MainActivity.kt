package com.codestallions.spacemmo.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.codestallions.spacemmo.R
import com.codestallions.spacemmo.SpaceMMO
import com.google.android.material.navigation.NavigationView

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    var toolbar: Toolbar? = null
    var drawerLayout: DrawerLayout? = null
    var navController: NavController? = null
    private lateinit var navigationView: NavigationView
    private lateinit var logoutButton: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        toolbar = findViewById(R.id.toolbar)
        logoutButton = findViewById(R.id.menu_logout)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController!!, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController!!)
        navigationView.setNavigationItemSelectedListener(this)
        logoutButton.setOnClickListener {
            SpaceMMO.getAuth().signOut()
            navigateToLogin()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout)
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        drawerLayout!!.closeDrawers()
        when (menuItem.itemId) {
            //                NavDeepLinkRequest navDeepLinkRequest = new NavDeepLinkRequest(new Intent());
            R.id.menu_star_map_button -> navController!!.navigate(R.id.starmapFragment)
            R.id.menu_system_button -> navController!!.navigate(R.id.systemFragment)
            R.id.menu_scanners_button -> navController!!.navigate(R.id.scannersFragment)
            R.id.menu_ship_and_crew_button -> navController!!.navigate(R.id.shipCrewFragment)
            R.id.menu_contracts_button -> navController!!.navigate(R.id.contractsFragment)
            R.id.menu_communications_button -> navController!!.navigate(R.id.communicationsFragment)
            R.id.menu_organization_button -> navController!!.navigate(R.id.organizationFragment)
        }
        return true
    }
}