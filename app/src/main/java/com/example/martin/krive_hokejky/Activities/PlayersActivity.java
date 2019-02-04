package com.example.martin.krive_hokejky.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.martin.krive_hokejky.R;
import com.example.martin.krive_hokejky.Utilities;

import static com.example.martin.krive_hokejky.Constants.LOG_LOTTIE;

public class PlayersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_activty);

        Utilities.log(LOG_LOTTIE, "PLAYERS ACTIVITY");
    }
}
