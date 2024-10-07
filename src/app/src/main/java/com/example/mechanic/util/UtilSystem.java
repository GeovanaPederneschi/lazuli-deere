package com.example.mechanic.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.example.mechanic.R;

public class UtilSystem {
    public static class DialogProgress extends Dialog {

        public DialogProgress(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_progress);
            setCancelable(false);
        }
    }

}
