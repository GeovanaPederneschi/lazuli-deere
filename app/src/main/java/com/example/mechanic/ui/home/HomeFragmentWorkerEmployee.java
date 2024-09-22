package com.example.mechanic.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mechanic.R;
import com.example.mechanic.databinding.FragmentHomeBinding;
import com.example.mechanic.dominio.person.Employee;
import com.facebook.shimmer.ShimmerFrameLayout;

public class HomeFragmentWorkerEmployee extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    private ShimmerFrameLayout shimmerFrameLayout;

    private Boolean isLoggedUser = false;
    private Dialog mDialog;

    private Employee byIdEmployee;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }



    public void onClick (View view, Intent intent){
        view.setOnClickListener(v -> {
            startActivity(intent);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}