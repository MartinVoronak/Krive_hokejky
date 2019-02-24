package com.example.martin.krive_hokejky;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.martin.krive_hokejky.DataObjects.Match;

import java.util.List;

public class BasicAdapterShowMatches extends ArrayAdapter<Match> {
    List<Match> futureMatches = null;
    private Context context;

    public BasicAdapterShowMatches(Context context, List<Match> menuAdapter) {
        super(context, R.layout.single_row_show_match, menuAdapter);
        this.futureMatches = menuAdapter;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Utilities.log(Constants.LOG_DATEPICKER, position+". Creating object");
        LayoutInflater listInflater = LayoutInflater.from(getContext());
        View customView = listInflater.inflate(R.layout.single_row_show_match, parent, false);

        Match futureMatch = futureMatches.get(position);
        String[] parts = futureMatch.getEuropeDateMatch().split(" ", 2);
        String date = parts[0];
        String time = parts[1];

        TextView txtEuropeDate = (TextView) customView.findViewById(R.id.txtShowDate);
        txtEuropeDate.setText(date);

        TextView txtTime = (TextView) customView.findViewById(R.id.txtShowTime);
        txtTime.setText(time);

        //show calendar if user want to see more details
        String[] dateParts = date.split("\\.");
        final int day = Integer.parseInt(dateParts[0]);
        final int month = Integer.parseInt(dateParts[1]);
        final int year = Integer.parseInt(dateParts[2]);
//        Utilities.log(Constants.LOG_DATEPICKER, "day: "+day+" month: "+month+" year: "+year);

        ImageView img = (ImageView) customView.findViewById(R.id.imgShowMatch);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(context, R.style.FullScreenDialogTheme, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        //do nothing
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });

        return customView;
    }
}