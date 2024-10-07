package com.example.mechanic.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mechanic.R;
import com.example.mechanic.databinding.FragmentHomeBinding;
import com.example.mechanic.dominio.objects.ReceiptPart;
import com.example.mechanic.dominio.objects.StatusDirection;
import com.example.mechanic.dominio.person.Employee;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

public class HomeFragmentWorkerEmployee extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    private ShimmerFrameLayout shimmerFrameLayout;

    private Boolean isLoggedUser = false;
    private Dialog mDialog;

    private Employee byIdEmployee;
    private Handler handler = new Handler();
    private boolean isMoving = false;
    private boolean isMoving2 = false;

    private int idGenerateIconLocation = View.generateViewId();

    public interface OnDataPass {
        void onDataPass(List<ReceiptPart> data);
    }

    private OnDataPass dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context; // Converta o contexto em OnDataPass
    }

    // Método para receber dados
    public void updateUI(List<ReceiptPart> data) {
        if(data.get(0).getReceipt().getStatusDirection()== StatusDirection.PEGANDO_PECA && !isMoving){
            isMoving = true;
            View myView = getView().findViewById(idGenerateIconLocation);
            if (myView != null) {
                ((ViewGroup) myView.getParent()).removeView(myView); // Remove a view
            }
            addLocationPoint(37, 540);
            int [] xPositions = {195, 190, 185, 180, 175, 170, 165, 160, 155, 150, 145, 140, 135, 130, 125, 120, 115, 110, 105, 100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 35, 35, 35, 35, 35};
            int [] yPositions = {470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 475, 480, 485, 490, 500};
            startMovingImage(xPositions,yPositions);
        }else if (data.get(0).getReceipt().getStatusDirection()== StatusDirection.ENTREGANDO_PECA && !isMoving2){
            isMoving2 = true;
            View myView = getView().findViewById(idGenerateIconLocation);
            if (myView != null) {
                ((ViewGroup) myView.getParent()).removeView(myView); // Remove a view
            }
            addLocationPoint(320, 5);
            // y = 470, y = 300, y = 210, y =5
            int[] xPositions = {35, 35, 35, 35, 35, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115, 120, 125, 130, 135, 140, 145, 150, 155, 160, 165, 170, 175, 180, 185, 190, 195, 200, 205, 210, 215, 220, 225, 230, 235, 240, 245, 250, 255, 260, 265, 270, 275, 280, 285, 290, 295, 300, 305, 310, 315, 320, 325, 330, 335, 340, 345, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 345, 340, 335, 330, 325, 320, 320, 320, 320, 320, 320, 320, 320, 320, 320, 320, 320, 320, 320, 320, 320, 320, 320, 320, 325, 330, 335, 340, 345, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350, 350};
            int[] yPositions = {500, 490, 485, 480, 475, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 470,  470, 470, 470, 470, 470, 470, 470, 470, 470, 470, 465, 460, 455, 450, 445, 440, 435, 430, 425, 420, 415, 410, 405, 400, 395, 390, 385, 380, 375, 370, 365, 360, 355, 350, 345, 340, 335, 330, 325, 320, 315, 310, 305, 300, 300, 300, 300, 300, 300, 300, 295, 290, 285, 280, 275, 270, 265, 260, 255, 250, 245, 240, 235, 230, 225, 220, 215, 210, 210, 210, 210, 210, 210, 210, 205, 200, 195,  190, 185, 180, 175, 170, 165, 160, 155, 150, 145, 140, 135, 130, 125, 120, 115, 110, 105, 100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30, 25, 20, 15, 10, 5};
            startMovingImage(xPositions,yPositions);
        }

    }

    private void startMovingImage(int[] xPositions,int[] yPositions) {
        final int[] currentIndex = {0};
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex[0] < xPositions.length) {
                    moveImageToPosition(xPositions[currentIndex[0]], yPositions[currentIndex[0]]);
                    currentIndex[0]++;

                    // Verifica se ainda há posições para mover
                    if (currentIndex[0] < xPositions.length) {
                        // Se sim, agendar a próxima mudança de posição
                        handler.postDelayed(this, 100);  // Mudar a cada 1 segundo
                    }
                }
            }
        }, 100);  // Primeira mudança em 1 segundo
    }

    private void addLocationPoint(int x, int y) {

        int xInPx = dpToPx(x);
        int yInPx = dpToPx(y);

        // Cria um novo ponto (View) com um ícone de ponto de localização
        View point = new View(getContext());
        point.setBackgroundResource(R.drawable.icon_destino);
        point.setId(idGenerateIconLocation);

        int size = 40; // Tamanho do ponto em px

        // Configura os parâmetros de layout, incluindo as margens X e Y
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size);
        params.leftMargin = xInPx;
        params.topMargin = yInPx;

        // Adiciona o ponto ao FrameLayout com as margens configuradas
        binding.frameLayoutImages.addView(point, params);
    }

    private void moveImageToPosition(int x, int y) {

        int xInPx = dpToPx(x);
        int yInPx = dpToPx(y);

        // Pegar o LayoutParams da ImageView
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) binding.vehiclePoint.getLayoutParams();

        // Alterar as margens para mover a ImageView
        layoutParams.leftMargin = xInPx;  // Definir a nova posição X
        layoutParams.topMargin = yInPx;   // Definir a nova posição Y

        // Aplicar as mudanças
        binding.vehiclePoint.setLayoutParams(layoutParams);
    }

    private int dpToPx(int dp) {
        // Obtém a densidade da tela e converte o valor de dp para pixels
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.vehiclePoint.setOnClickListener(v->{
            Log.v("DevApp","teste");
        });

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