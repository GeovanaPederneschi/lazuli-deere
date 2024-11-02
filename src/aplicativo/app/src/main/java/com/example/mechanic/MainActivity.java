package com.example.mechanic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mechanic.controller.AppController;
import com.example.mechanic.databinding.ActivityBinding;
import com.example.mechanic.dominio.person.TypeEmployee;
import com.example.mechanic.ui.MainWorkerEmployee;
import com.example.mechanic.ui.MainManagerEmployee;
import com.example.mechanic.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity{

    private ActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!AppController.validateGooglePlayServices(MainActivity.this)) {
            Toast.makeText(this, "GooglePlayServices não configurado...", Toast.LENGTH_LONG).show();
        }

        binding = ActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Log.v("DevApp Fluxo1", "Aplicação Iniciada");

        Intent intentActivityLogin = new Intent(this, LoginActivity.class);
        someActivityResultLauncher.launch(intentActivityLogin);

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                    Log.v("DevApp Fluxo1", "Login efetuado com sucesso");
                    TypeEmployee typeEmployee = (TypeEmployee) result.getData().getSerializableExtra("typeEmployee");
                    if(typeEmployee == TypeEmployee.WORKER){
                        Intent intent = new Intent(this, MainWorkerEmployee.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(typeEmployee == TypeEmployee.MANAGER){
                        Intent intent = new Intent(this, MainManagerEmployee.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });

}