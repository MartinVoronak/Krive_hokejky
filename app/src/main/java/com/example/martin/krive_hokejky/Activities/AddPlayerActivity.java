package com.example.martin.krive_hokejky.Activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.martin.krive_hokejky.APIcalls;
import com.example.martin.krive_hokejky.R;
import com.example.martin.krive_hokejky.Utilities;

public class AddPlayerActivity extends AppCompatActivity {

    public static EditText editName;;
    public static EditText editSurname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        editName = (EditText) findViewById(R.id.editName);
        editSurname = (EditText) findViewById(R.id.editSurname);

        //make sure first letter is capital
        editName.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        editSurname.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        final Button button = findViewById(R.id.btnAddPlayer);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.i("AddPlayerActivity", "btn send clicked");
                AlertDialog alertDialog = Utilities.createDialog(AddPlayerActivity.this, APIcalls.CREATE_PLAYER);
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
