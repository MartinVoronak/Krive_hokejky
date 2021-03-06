package com.example.martin.krive_hokejky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.martin.krive_hokejky.DataObjects.Player;

import java.util.List;

public class BasicAdapterPlayers extends ArrayAdapter<Player> {

    List<Player> players = null;

    public BasicAdapterPlayers(Context context, List<Player> menuAdapter) {
        super(context, R.layout.single_row, menuAdapter);
        this.players = menuAdapter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater listInflater = LayoutInflater.from(getContext());
        View customView = listInflater.inflate(R.layout.single_row, parent, false);

        Player player = players.get(position);
        TextView fullNameText = (TextView) customView.findViewById(R.id.txtFullName);
        fullNameText.setText(player.getSurname() + " " + player.getFirstName());
        TextView fullStatistics = (TextView) customView.findViewById(R.id.txtStatistics);
        fullStatistics.setText("Góly: "+player.getGoals() + "    Asistencie: " + player.getAssissts()+"        Hodnotenie: "+player.getRating());

        return customView;
    }
}
