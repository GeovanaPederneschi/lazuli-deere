package com.example.mechanic.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mechanic.R;
import com.example.mechanic.controller.AppController;
import com.example.mechanic.databinding.MainTechinicianBinding;
import com.example.mechanic.ui.dashboard.DashboardFragment;
import com.example.mechanic.ui.home.HomeFragmentManagerEmployee;
import com.example.mechanic.ui.home.HomeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainManagerEmployee extends AppCompatActivity {

    private MainTechinicianBinding binding;
    private HomeViewModel homeViewModel;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Dialog mDialog2;
    private Boolean isLoggedUser;
    FloatingActionButton fab;
    BottomNavigationView bottomNavigationView;

    private HomeFragmentManagerEmployee homeFragmentManagerEmployee;


    @SuppressLint({"RestrictedApi", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!AppController.validateGooglePlayServices(MainManagerEmployee.this)) {
            Toast.makeText(this, "GooglePlayServices não configurado...", Toast.LENGTH_LONG).show();
        }


        binding = MainTechinicianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = binding.navView;
        fab = binding.fab;
        drawerLayout = binding.drawerLayout;
        NavigationView navLateralView = binding.navLateralView;
        Toolbar toolbar = binding.toolbar;

        replaceFragment(new HomeFragmentManagerEmployee());
        // acho que é isso

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (R.id.navigation_home == itemId) {
                replaceFragment(new HomeFragmentManagerEmployee());
            } else if (R.id.navigation_dashboard == itemId) {
                replaceFragment(new DashboardFragment());
            }

            return true;
        });


        View headerView = navLateralView.getHeaderView(0);
        TextView tvEmployeeName = headerView.findViewById(R.id.employeeName);
        TextView tvEmployeeEmail = headerView.findViewById(R.id.employeeEmail);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawerToggle, R.string.closeDrawerToggle);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem itemHome = menu.findItem(R.id.navigation_home);

        navLateralView.bringToFront();
        navLateralView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            Log.d("DevApp Fluxo1", "onNavigationItemSelected (direct): " + itemId);

            // Adicione seus casos aqui
            if (itemId == R.id.lateral_menu_home) {
                replaceFragment(new HomeFragmentManagerEmployee());
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.lateral_menu_dashboard) {
                replaceFragment(new DashboardFragment());
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.lateral_menu_notifications) {
                // Adicione aqui o código para o item "Notifications"
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.lateral_menu_order_procedures) {
                // tirei coisa ver la
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.lateral_menu_settings) {
                // Adicione aqui o código para o item "Settings" dentro do submenu "Profile"
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.lateral_menu_account) {
                // Adicione aqui o código para o item "Account" dentro do submenu "Profile"
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });




        Log.v("DevApp Fluxo1", "Aplicação Iniciada");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lateral_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincronizar o DrawerToggle após a criação (necessário para atualizar o ícone de menu)
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Passar alterações de configuração para o DrawerToggle
        toggle.onConfigurationChanged(newConfig);
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}