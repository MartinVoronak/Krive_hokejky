package com.example.martin.krive_hokejky.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;

import com.example.martin.krive_hokejky.APIcalls;
import com.example.martin.krive_hokejky.BasicAdapterAddMatches;
import com.example.martin.krive_hokejky.Constants;
import com.example.martin.krive_hokejky.DataObjects.Match;
import com.example.martin.krive_hokejky.R;
import com.example.martin.krive_hokejky.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class AddMatchActivity extends AppCompatActivity {

    public static List<Match> futureMatches;
    public static ListView matchesListView;
    public static ArrayAdapter<Match> futureMatchesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);

        futureMatches = new ArrayList<>();
        Calendar mcurrentDate = Calendar.getInstance();
        final int[] mYear = {mcurrentDate.get(Calendar.YEAR)};
        final int[] mMonth = {mcurrentDate.get(Calendar.MONTH)};
        final int[] mDay = {mcurrentDate.get(Calendar.DAY_OF_MONTH)};
        final int hour = Constants.preferedHourMatch;
        final int minute = Constants.preferedMinuteMatch;

        //we want closest wednesday
        final GregorianCalendar calendarDate = new GregorianCalendar(mYear[0], mMonth[0], mDay[0]);
        while (calendarDate.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
            calendarDate.add(Calendar.DATE, 1);
        }
        mDay[0] = calendarDate.get(Calendar.DAY_OF_MONTH);
        //MONTH DATE YEAR!!!
        String myFormat = "MM.dd.yyyy HH:mm:ss";
        String myEuropeFormat = "dd.MM.yyyy HH:mm:ss";
        final SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.GERMANY);
        final SimpleDateFormat europeDateFormat = new SimpleDateFormat(myEuropeFormat, Locale.GERMANY);


        futureMatchesAdapter = new BasicAdapterAddMatches(this, futureMatches);
        matchesListView = (ListView) findViewById(R.id.listViewAddMatch);
        matchesListView.setAdapter(futureMatchesAdapter);

        FloatingActionButton fabAddMatch = findViewById(R.id.fabAddMatch);
        fabAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog mDatePicker = new DatePickerDialog(AddMatchActivity.this, R.style.FullScreenDialogTheme, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_WEEK, selectedday);

                        mDay[0] = selectedday;
                        mMonth[0] = selectedmonth;
                        mYear[0] = selectedyear;

                        //show timepicker
                        TimePickerDialog mTimePicker = new TimePickerDialog(AddMatchActivity.this, R.style.FullScreenDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                                String dateString = dateFormat.format(new Date(mYear[0] - 1900, mMonth[0], mDay[0], selectedHour, selectedMinute, 0));
                                String europeDateString = europeDateFormat.format(new Date(mYear[0] - 1900, mMonth[0], mDay[0], selectedHour, selectedMinute, 0));

                                Match futureMatch = new Match();
                                futureMatch.setAmericaDateMatch(dateString);
                                futureMatch.setEuropeDateMatch(europeDateString);

                                Date today = new Date();
                                Date selected = new Date(mYear[0] - 1900, mMonth[0], mDay[0], selectedHour, selectedMinute, 0);

                                int value = selected.compareTo(today);
                                if (value < 0){
                                    AlertDialog alertDialog = Utilities.createDialog(AddMatchActivity.this, null, "Nemôžeš vybrať minulý dátum!");
                                    alertDialog.show();
                                }else {
                                    futureMatches.add(futureMatch);
                                    sortListView();
                                    futureMatchesAdapter.notifyDataSetChanged();
                                    matchesListView.setAdapter(futureMatchesAdapter);

                                    Utilities.log(Constants.LOG_DATEPICKER, "List:");
                                    Utilities.log(Constants.LOG_DATEPICKER, "" + futureMatches);
                                    Utilities.log(Constants.LOG_DATEPICKER, "Full date picked: " + dateString);
                                }
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.show();
                        //end of timepicker

                    }
                }, mYear[0], mMonth[0], mDay[0]);
                mDatePicker.show();
            }
        });

        FloatingActionButton fabSendMatch = findViewById(R.id.fabSendMatch);
        fabSendMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!futureMatches.isEmpty()){
                    AlertDialog alertDialog = Utilities.createDialog(AddMatchActivity.this, APIcalls.CREATE_MATCH, null);
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = Utilities.createDialog(AddMatchActivity.this, null, "Nepridal si žiaden dátum!");
                    alertDialog.show();
                }
            }
        });
    }

    public static void sortListView(){

        String myEuropeFormat = "dd.MM.yyyy HH:mm:ss";
        final SimpleDateFormat dateFormat = new SimpleDateFormat(myEuropeFormat, Locale.GERMANY);

        //sort by date so we can see upcoming matches first
        Collections.sort(futureMatches, new Comparator<Match>() {
            Date date1;
            Date date2;

            public int compare(Match m1, Match m2) {
                try {
                    date1 = dateFormat.parse(m1.getEuropeDateMatch());
                    date2 = dateFormat.parse(m2.getEuropeDateMatch());


                } catch (ParseException e) {
                    Utilities.log(Constants.LOG_DATEPICKER, "Wrong date format occured, parsing error");
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            }
        });

        matchesListView.setAdapter(futureMatchesAdapter);
    }
}
