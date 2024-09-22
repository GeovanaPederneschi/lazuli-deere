package com.example.mechanic.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.mechanic.databinding.MainMechanicalBinding;
import com.example.mechanic.ui.dashboard.DashboardFragment;
import com.example.mechanic.ui.home.HomeFragmentWorkerEmployee;
import com.example.mechanic.ui.home.HomeFragmentManagerEmployee;
import com.example.mechanic.ui.home.HomeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainWorkerEmployee extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MainMechanicalBinding binding;
    private HomeViewModel homeViewModel;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private Dialog mDialog2;
    private Boolean isLoggedUser;
    FloatingActionButton fab;
    BottomNavigationView bottomNavigationView;

    private HomeFragmentManagerEmployee homeFragmentManagerEmployee;


    @SuppressLint({"RestrictedApi", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!AppController.validateGooglePlayServices(MainWorkerEmployee.this)) {
            Toast.makeText(this, "GooglePlayServices não configurado...", Toast.LENGTH_LONG).show();
        }


        binding = MainMechanicalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = binding.navView;
        fab = binding.fab;

        fab.setOnClickListener(v->{
            showBottomDialog();
        });

        drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navLateralView;
        Toolbar toolbar = binding.toolbar;

        replaceFragment(new HomeFragmentWorkerEmployee());
        // acho que é isso

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (R.id.navigation_home == itemId) {
                replaceFragment(new HomeFragmentWorkerEmployee());
            } else if (R.id.navigation_dashboard == itemId) {
                replaceFragment(new DashboardFragment());
            }

            return true;
        });

        drawerLayout = binding.drawerLayout;
        NavigationView navLateralView = binding.navLateralView;
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawerToggle, R.string.closeDrawerToggle);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        setSupportActionBar(toolbar);

        View headerView = navLateralView.getHeaderView(0);
        TextView tvEmployeeName = headerView.findViewById(R.id.employeeName);
        TextView tvEmployeeEmail = headerView.findViewById(R.id.employeeEmail);


        navLateralView.bringToFront();
        navLateralView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            Log.d("DevApp Fluxo1", "onNavigationItemSelected (direct): " + itemId);

            // Adicione seus casos aqui
            if (itemId == R.id.lateral_menu_home) {
                replaceFragment(new HomeFragmentWorkerEmployee());
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

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout_main_order);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.lateral_menu_home) {
            Toast.makeText(MainWorkerEmployee.this, "testNavLateral", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}