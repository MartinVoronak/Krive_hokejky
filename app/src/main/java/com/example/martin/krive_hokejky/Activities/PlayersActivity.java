package com.example.martin.krive_hokejky.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.martin.krive_hokejky.APIcalls;
import com.example.martin.krive_hokejky.BasicAdapterPlayers;
import com.example.martin.krive_hokejky.DataObjects.Player;
import com.example.martin.krive_hokejky.R;
import com.example.martin.krive_hokejky.Utilities;

import java.util.List;

import static com.example.martin.krive_hokejky.Constants.LOG_LOTTIE;

public class PlayersActivity extends AppCompatActivity {

    private List<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_activty);
        Utilities.log(LOG_LOTTIE, "PLAYERS ACTIVITY");

        //TODO call api once and store, add refresh button to re-fetch players
        this.players = APIcalls.players;

        if (players!=null && !players.isEmpty()){
            ArrayAdapter<Player> playersAdapter = new BasicAdapterPlayers(this, players);
            ListView playersListView = (ListView)findViewById(R.id.listViewPlayers);
            playersListView.setAdapter(playersAdapter);
        }
        else{
            Toast.makeText(PlayersActivity.this, "Vyskytol sa problém na strane servera. Vyskúšajte to neskôr.", Toast.LENGTH_LONG).show();
        }
    }
}
