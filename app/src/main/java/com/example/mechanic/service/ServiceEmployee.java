package com.example.mechanic.service;

import android.util.Log;

import com.example.mechanic.dominio.person.Employee;
import com.example.mechanic.repository.RepositoryEmployee;

import org.json.JSONException;

public class ServiceEmployee {
    public static Employee findByEmailAndPassword(String email, String password){
        return RepositoryEmployee.FindByEmailAndPassword.build(email,password).onPosExecute();
    }
}
