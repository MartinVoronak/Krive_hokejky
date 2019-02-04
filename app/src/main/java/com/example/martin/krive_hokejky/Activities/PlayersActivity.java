package com.example.martin.krive_hokejky.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.martin.krive_hokejky.APIcalls;
import com.example.martin.krive_hokejky.BasicAdapter;
import com.example.martin.krive_hokejky.Player;
import com.example.martin.krive_hokejky.R;
import com.example.martin.krive_hokejky.Utilities;

import java.util.ArrayList;
import java.util.List;

import static com.example.martin.krive_hokejky.Constants.LOG_LOTTIE;

public class PlayersActivity extends AppCompatActivity {

//    private List<Player> players = new ArrayList<>();
    private List<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_activty);

        this.players = APIcalls.players;

        ArrayAdapter<Player> playersAdapter = new BasicAdapter(this, players);
        ListView playersListView = (ListView)findViewById(R.id.playersListView);
        playersListView.setAdapter(playersAdapter);

        Utilities.log(LOG_LOTTIE, "PLAYERS ACTIVITY");
    }
}
