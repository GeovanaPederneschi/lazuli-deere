package com.example.mechanic.ui.home;

import android.widget.Button;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<Button> btnNewService;
    private final MutableLiveData<Button> btnServices;


    public HomeViewModel() {
        btnNewService = new MutableLiveData<>();
        btnServices = new MutableLiveData<>();
    }




    public MutableLiveData<Button> getBtnNewService() {
        return btnNewService;
    }

    public MutableLiveData<Button> getBtnServices() {
        return btnServices;
    }
}