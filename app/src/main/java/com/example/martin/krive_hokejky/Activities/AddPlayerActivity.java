package com.example.martin.krive_hokejky.Activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.martin.krive_hokejky.APIcalls;
import com.example.martin.krive_hokejky.R;
import com.example.martin.krive_hokejky.Utilities;

public class AddPlayerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
            Intent intent = new Intent(AddPlayerActivity.this, LottieResultActivity.class);
            intent.putExtra("action", APIcalls.RETRIEVE_PLAYERS);
            startActivity(intent);

        } else if (id == R.id.addPlayer) {
            Intent intent = new Intent(AddPlayerActivity.this, AddPlayerActivity.class);
            startActivity(intent);

        } else if (id == R.id.playedMatches) {
            Intent intent = new Intent(AddPlayerActivity.this, PlayedMatchesActivity.class);
            startActivity(intent);

        } else if (id == R.id.addMatch) {
            Intent intent = new Intent(AddPlayerActivity.this, AddMatchActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
