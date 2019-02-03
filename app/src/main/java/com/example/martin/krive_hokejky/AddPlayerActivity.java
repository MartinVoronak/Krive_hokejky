package com.example.martin.krive_hokejky;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;

import okhttp3.internal.Util;

public class AddPlayerActivity extends AppCompatActivity {

    public static EditText editName;;
    public static EditText editSurname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);



        editName = (EditText) findViewById(R.id.editName);
        editSurname = (EditText) findViewById(R.id.editSurname);

        final Button button = findViewById(R.id.btnAddPlayer);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.i("AddPlayerActivity", "btn send clicked");
                AlertDialog alertDialog = Utilities.createDialog(AddPlayerActivity.this, APIcalls.CREATE);
                alertDialog.show();

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
