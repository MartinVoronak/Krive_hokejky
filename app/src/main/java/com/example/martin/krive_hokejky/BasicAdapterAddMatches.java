package com.example.martin.krive_hokejky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.martin.krive_hokejky.DataObjects.Match;

import java.util.List;

public class BasicAdapterAddMatches extends ArrayAdapter<Match> {
    List<Match> futureMatches = null;

    public BasicAdapterAddMatches(Context context, List<Match> menuAdapter) {
        super(context, R.layout.single_row_add_match, menuAdapter);
        this.futureMatches = menuAdapter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Utilities.log(Constants.LOG_DATEPICKER, position+". Creating object");
        LayoutInflater listInflater = LayoutInflater.from(getContext());
        View customView = listInflater.inflate(R.layout.single_row_add_match, parent, false);

        Match futureMatch = futureMatches.get(position);
        String[] parts = futureMatch.getEuropeDateMatch().split(" ", 2);
        String date = parts[0];
        String time = parts[1];

        TextView txtEuropeDate = (TextView) customView.findViewById(R.id.txtAddedDate);
        txtEuropeDate.setText(date);

        TextView txtTime = (TextView) customView.findViewById(R.id.txtAddedTime);
        txtTime.setText(time);

        return customView;
    }
}
