package com.example.mechanic.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mechanic.databinding.FragmentHomeBinding;
import com.facebook.shimmer.ShimmerFrameLayout;

public class HomeFragmentManagerEmployee extends Fragment {

    private FragmentHomeBinding binding;
    private ShimmerFrameLayout shimmerFrameLayout;

    private Boolean isLoggedUser = false;

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
        //taskLongAssignmentWorkOrder.stopPeriodicExecution();
    }
}