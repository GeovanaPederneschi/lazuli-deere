package com.example.mechanic.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aspose.pdf.operators.Re;
import com.example.mechanic.R;
import com.example.mechanic.controller.AppController;
import com.example.mechanic.databinding.BottomsheetlayoutMainOrderBinding;
import com.example.mechanic.databinding.MainMechanicalBinding;
import com.example.mechanic.dominio.objects.Receipt;
import com.example.mechanic.dominio.objects.ReceiptPart;
import com.example.mechanic.dominio.objects.StatusReceipt;
import com.example.mechanic.dominio.person.Employee;
import com.example.mechanic.repository.RepositoryEmployee;
import com.example.mechanic.service.ServiceReceipt;
import com.example.mechanic.ui.dashboard.DashboardFragment;
import com.example.mechanic.ui.home.HomeFragmentWorkerEmployee;
import com.example.mechanic.ui.home.HomeFragmentManagerEmployee;
import com.example.mechanic.ui.home.HomeViewModel;
import com.example.mechanic.util.AsyncTaskScheduleExecutorService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainWorkerEmployee extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeFragmentWorkerEmployee.OnDataPass{

    private MainMechanicalBinding binding;
    private HomeViewModel homeViewModel;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private Dialog mDialog2;
    private Boolean isLoggedUser;
    FloatingActionButton fab;
    BottomNavigationView bottomNavigationView;

    private HomeFragmentManagerEmployee homeFragmentManagerEmployee;
    MyBottomSheetFragment bottomSheetFragment;
    private LoadReceiptInProgress taskLoadReceiptInProgress;


    @SuppressLint({"RestrictedApi", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!AppController.validateGooglePlayServices(MainWorkerEmployee.this)) {
            Toast.makeText(this, "GooglePlayServices não configurado...", Toast.LENGTH_LONG).show();
        }

        Employee employee = RepositoryEmployee.findEmployee(getApplicationContext());


        binding = MainMechanicalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.drawerLayout.setOnClickListener(v->{
            Log.v("DevApp","GGGGGGGGGGGGGGGGGG");
        });

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

        bottomSheetFragment = new MyBottomSheetFragment();

        taskLoadReceiptInProgress = new LoadReceiptInProgress();
        taskLoadReceiptInProgress.setDelayMillis(1000);
        taskLoadReceiptInProgress.setRepeating(true); // Define a tarefa para execução periódica
        taskLoadReceiptInProgress.startPeriodicExecution(employee);

    }

    @Override
    public void onDataPass(List<ReceiptPart> data) {
        HomeFragmentWorkerEmployee fragment = (HomeFragmentWorkerEmployee) getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (fragment != null) {
            fragment.updateUI(data); // Passe os dados para o Fragment
        }
    }

    public static class MyBottomSheetFragment extends BottomSheetDialogFragment {

        BottomsheetlayoutMainOrderBinding sheetLayoutBottomBinding;
        List<ReceiptPart> receiptPartList;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            sheetLayoutBottomBinding = BottomsheetlayoutMainOrderBinding.inflate(inflater, container, false);
            return sheetLayoutBottomBinding.getRoot();
        }

            private void setupBottomSheetBehavior (BottomSheetDialog dialog){
                View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);

                // Configurar altura mínima e estado inicial colapsado
                behavior.setPeekHeight(200);
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                // Impedir o BottomSheet de fechar ao deslizar para baixo
                behavior.setHideable(false); // Não permite fechar

                // Permitir que o BottomSheet seja levantado e abaixado
                behavior.setDraggable(true);
            }


            @Override
            public void onStart () {
                super.onStart();
                BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                if (dialog != null) {
                    dialog.getWindow().setDimAmount(0f); // O fundo da Activity não escurece

                    sheetLayoutBottomBinding.button.setOnClickListener(v -> {
                        new StatusDone().execute(receiptPartList.get(0).getReceipt());
                    });

                    setupBottomSheetBehavior(dialog);
                }
            }


            public class StatusDone extends AsyncTaskScheduleExecutorService<Receipt, Void, Boolean> {

                @Override
                protected Boolean doInBackground(Receipt receipt) {
                    receipt.setStatusReceipt(StatusReceipt.DONE);
                    return ServiceReceipt.updateStatusReceipt(receipt);
                }

                @Override
                protected void onPostExecute(Boolean receiptPartList) {
                    sheetLayoutBottomBinding.conteudo.setVisibility(View.GONE);
                    sheetLayoutBottomBinding.textView6.setText("Ponto de Coleta A2");
                    sheetLayoutBottomBinding.textView6.setText("Ponto de Estoque F5");
                    sheetLayoutBottomBinding.linearProgress.setVisibility(View.VISIBLE);
                }
            }
    }


    private void showBottomDialog() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public class LoadReceiptInProgress extends AsyncTaskScheduleExecutorService<Employee,Void, List<ReceiptPart>>{

        @Override
        protected List<ReceiptPart> doInBackground(Employee employee) {
            return ServiceReceipt.findReceiptInProgressByIdCar(employee.getCar());
        }

        @Override
        protected void onPostExecute(List<ReceiptPart> receiptPartList) {
            if(receiptPartList.size()>0){
                if (bottomSheetFragment.isVisible()){
                    bottomSheetFragment.sheetLayoutBottomBinding.linearProgress.setVisibility(View.GONE);
                    bottomSheetFragment.sheetLayoutBottomBinding.conteudo.setVisibility(View.VISIBLE);
                    bottomSheetFragment.receiptPartList = receiptPartList;
                }
                onDataPass(receiptPartList);
            } else {
                if (bottomSheetFragment.isVisible()){
                    bottomSheetFragment.sheetLayoutBottomBinding.linearProgress.setVisibility(View.VISIBLE);
                    bottomSheetFragment.sheetLayoutBottomBinding.conteudo.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(taskLoadReceiptInProgress!=null && taskLoadReceiptInProgress.isRepeating()){
           taskLoadReceiptInProgress.stopPeriodicExecution();
        }
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