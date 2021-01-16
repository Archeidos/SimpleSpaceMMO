package com.codestallions.spacemmo.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.SpaceMMO;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavController navController;
    public NavigationView navigationView;

    private TextView logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNavigation();
    }

    private void setupNavigation() {
        toolbar = findViewById(R.id.toolbar);
        logoutButton = findViewById(R.id.menu_logout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        logoutButton.setOnClickListener(view -> {
            SpaceMMO.getAuth().signOut();
            navigateToLogin();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.menu_star_map_button:
//                NavDeepLinkRequest navDeepLinkRequest = new NavDeepLinkRequest(new Intent());
                navController.navigate(R.id.starmapFragment);
                break;
            case R.id.menu_system_button:
                navController.navigate(R.id.systemFragment);
                break;
            case R.id.menu_scanners_button:
                navController.navigate(R.id.scannersFragment);
                break;
            case R.id.menu_ship_and_crew_button:
                navController.navigate(R.id.shipCrewFragment);
                break;
            case R.id.menu_contracts_button:
                navController.navigate(R.id.contractsFragment);
                break;
            case R.id.menu_communications_button:
                navController.navigate(R.id.communicationsFragment);
                break;
            case R.id.menu_organization_button:
                navController.navigate(R.id.organizationFragment);
                break;
        }
        return true;
    }
}