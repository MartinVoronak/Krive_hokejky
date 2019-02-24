package com.example.martin.krive_hokejky;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.martin.krive_hokejky.Activities.AddMatchActivity;
import com.example.martin.krive_hokejky.Activities.LottieResultActivity;
import com.example.martin.krive_hokejky.Activities.PlayersActivity;
import com.example.martin.krive_hokejky.DataObjects.Player;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utilities {

    public static String animationName = "";

    public static void setAnimationName(LottieAnimationView animationView, String value) {
        animationView.setAnimation(value);
        animationName = value;
    }
    public static void log(String tag, String s){
        Log.i(tag, s);
    }

    public static AlertDialog createDialog(final Context context, final APIcalls action, final String text){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        if (action == null){
            alertDialogBuilder.setMessage(text);

            if (text.equals("Zadaj heslo")){

                final EditText input = new EditText(context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                alertDialogBuilder.setView(input);

                alertDialogBuilder.setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utilities.log(Constants.LOG_ADD_PLAYER, "OK clicked");
                                if (input.getText().toString().equals("1234")){

                                    AlertDialog alertDialog = Utilities.createDialog(context, APIcalls.CREATE_MATCH, null);
                                    alertDialog.show();

                                }
                                else {
                                    Toast.makeText(context, "Nesprávne heslo.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else {

                alertDialogBuilder.setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utilities.log(Constants.LOG_ADD_PLAYER, "OK clicked");
                            }
                        });
            }
        }
        else {
            alertDialogBuilder.setMessage("Naozaj chceš vykonať túto akciu?");

            alertDialogBuilder.setPositiveButton("Áno",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            Utilities.log(Constants.LOG_ADD_PLAYER, "yes clicked");
                            Intent intent = new Intent(context, LottieResultActivity.class);
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
        }



        AlertDialog alertDialog = alertDialogBuilder.create();
        return alertDialog;
    }

    public static List<Player> sortPlayersNames(List<Player> playersList){

        Collections.sort(playersList, new Comparator<Player>() {
            @Override
            public int compare(Player s1, Player s2) {
                return s1.getSurname().compareToIgnoreCase(s2.getSurname());
            }
        });

        return playersList;
    }
}
