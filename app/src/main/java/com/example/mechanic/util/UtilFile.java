package com.example.mechanic.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class UtilFile {
    public static void readPdfFile(Uri uri, Context context) {
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(uri, "application/pdf");
        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent intent = Intent.createChooser(target, "Abrir arquivo");

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e("DevApp Fluxo1", "Nenhum aplicativo disponível para abrir PDF");
        }
    }
}
