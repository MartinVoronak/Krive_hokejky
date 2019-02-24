package com.example.martin.krive_hokejky.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.utils.Utils;
import com.backendless.Backendless;
import com.example.martin.krive_hokejky.APIcalls;
import com.example.martin.krive_hokejky.BasicAdapterAddMatches;
import com.example.martin.krive_hokejky.Constants;
import com.example.martin.krive_hokejky.DataObjects.Match;
import com.example.martin.krive_hokejky.DataObjects.Player;
import com.example.martin.krive_hokejky.R;
import com.example.martin.krive_hokejky.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static boolean firstLoad = true;
    private static  List<Match> futureMatches = new ArrayList<>();
    private static ArrayAdapter<Match> futureMatchesAdapter;
    private static ListView futureMatchesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Backendless.setUrl(Constants.SERVER_URL);
        Backendless.initApp(getApplicationContext(), Constants.APPLICATION_ID, Constants.API_KEY);

        futureMatchesAdapter = new BasicAdapterAddMatches(this, futureMatches);
        futureMatchesListView = (ListView)findViewById(R.id.listViewFutureMatches);
        futureMatchesListView.setAdapter(futureMatchesAdapter);

        futureMatchesListView.setClickable(true);
        futureMatchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                Match selectedMatch = (Match) futureMatchesAdapter.getItem(position);
                Intent intentMatches = new Intent(HomePageActivity.this, SignMatch.class);
                intentMatches.putExtra("selectedMatch", selectedMatch);
                //TODO send picked match and players
                startActivity(intentMatches);
            }
        });

        if (firstLoad) {
            firstLoad = false;

            Intent intentMatches = new Intent(HomePageActivity.this, LottieResultActivity.class);
            intentMatches.putExtra("action", APIcalls.RETRIEVE_FUTURE_MATCHES);
            startActivity(intentMatches);
        }
    }

    public static void setListViews(){
        futureMatches = APIcalls.matches;

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

        futureMatchesListView.setAdapter(futureMatchesAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.players) {
            Intent intent = new Intent(HomePageActivity.this, LottieResultActivity.class);
            intent.putExtra("action", APIcalls.RETRIEVE_PLAYERS);
            startActivity(intent);

        } else if (id == R.id.addPlayer) {
            Intent intent = new Intent(HomePageActivity.this, AddPlayerActivity.class);
            startActivity(intent);

        } else if (id == R.id.playedMatches) {
            Intent intent = new Intent(HomePageActivity.this, PlayedMatchesActivity.class);
            startActivity(intent);

        } else if (id == R.id.addMatch) {
            Intent intent = new Intent(HomePageActivity.this, AddMatchActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
