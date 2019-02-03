package com.example.martin.krive_hokejky;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;

public class Utilities {

    public static String animationName = "";


    public static void setAnimationName(String value) {
        animationName = value;
    }
    public static void log(String tag, String s){
        Log.i(tag, s);
    }

    public static AlertDialog createDialog(final Context context, final APIcalls action){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Naozaj chceš vykonať túto akciu?");

        alertDialogBuilder.setPositiveButton("Áno",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Utilities.log(Constants.LOG_ADD_PLAYER, "yes clicked");
//                      APIcalls.doAction(action, animationView);

                        Intent intent = new Intent(context, LottieResult.class);
                        intent.putExtra("action", action);
                        context.startActivity(intent);

                    }
                });

        alertDialogBuilder.setNegativeButton("Nie",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utilities.log(Constants.LOG_ADD_PLAYER, "no clicked");
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        return alertDialog;
    }
}
