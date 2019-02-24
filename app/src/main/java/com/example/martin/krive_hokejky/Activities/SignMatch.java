package com.example.martin.krive_hokejky.Activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.martin.krive_hokejky.APIcalls;
import com.example.martin.krive_hokejky.Constants;
import com.example.martin.krive_hokejky.DataObjects.Match;
import com.example.martin.krive_hokejky.DataObjects.Player;
import com.example.martin.krive_hokejky.R;
import com.example.martin.krive_hokejky.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SignMatch extends AppCompatActivity {

    private static List<Player> registeredPlayers = new ArrayList<>();
    private List<String> playersName = new ArrayList<>();
    private int selectedPlayer;
    public static Match selectedMatch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_match);

        selectedPlayer = 0;
        selectedMatch = (Match) getIntent().getSerializableExtra("selectedMatch");
        Utilities.log(Constants.LOG_SIGNMATCH, "selected match date: "+selectedMatch.getEuropeDateMatch());

        //sort players by surname
        this.registeredPlayers = APIcalls.players;
        Collections.sort(registeredPlayers, new Comparator<Player>() {
            @Override
            public int compare(Player s1, Player s2) {
                return s1.getSurname().compareToIgnoreCase(s2.getSurname());
            }
        });

        for (Player player : registeredPlayers) {
            playersName.add(player.getSurname() +" "+ player.getFirstName());
        }


        playersName.add(0, "Vyber hráča");


        Spinner spinner = (Spinner) findViewById(R.id.spinnerSelectPlayer);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, playersName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                selectedPlayer = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        final Button button = findViewById(R.id.btnSignPlayer);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (selectedPlayer != 0){
                    Utilities.log(Constants.LOG_SIGNMATCH, "selected player: "+registeredPlayers.get(selectedPlayer-1).getSurname());
                    selectedMatch.addPlayer(registeredPlayers.get(selectedPlayer-1));
//
                    AlertDialog alertDialog = Utilities.createDialog(SignMatch.this, APIcalls.SIGN_FUTURE_MATCH);
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = Utilities.createDialog(SignMatch.this, null);
                    alertDialog.show();
                }
            }
        });
    }
}
