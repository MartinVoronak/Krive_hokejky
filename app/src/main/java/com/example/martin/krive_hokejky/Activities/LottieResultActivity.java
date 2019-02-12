package com.example.martin.krive_hokejky.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.martin.krive_hokejky.APIcalls;
import com.example.martin.krive_hokejky.R;
import com.example.martin.krive_hokejky.Utilities;

import static com.example.martin.krive_hokejky.Constants.LOG_LOTTIE;

public class LottieResultActivity extends AppCompatActivity {

    //todo probably delete inAPI boolean
    public static LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_result);

        final APIcalls action = (APIcalls) getIntent().getSerializableExtra("action");

        animationView = (LottieAnimationView) findViewById(R.id.animation_load);
        Utilities.setAnimationName(animationView,"loading.json");

        Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            //after showing SUCCESS or ERROR
            @Override
            public void onAnimationEnd(Animator animation) {

                Utilities.log(LOG_LOTTIE, "onAnimationEnd, "+action.toString()+" animationName: "+Utilities.animationName );

                //if error gif stops, go to previous activity
                if (Utilities.animationName.equals("error.json")){
                    Utilities.log(LOG_LOTTIE, "An error occurred, returning");
                    Toast.makeText(LottieResultActivity.this, "Niekde nastal problém! Skontroluj svoje internetové pripojenie.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if (Utilities.animationName.equals("success.json") && (action.equals(APIcalls.CREATE_PLAYER) || action.equals(APIcalls.CREATE_MATCH))){
                    Intent intent = new Intent(LottieResultActivity.this, HomePageActivity.class);
                    finish();
                    startActivity(intent);
                }
            }

            //if we dont wanna show SUCCESS or ERROR
            @Override
            public void onAnimationCancel(Animator animation) {
                Utilities.log(LOG_LOTTIE, "onAnimationCancel, "+action.toString()+" animationName: "+Utilities.animationName+ " inAPI: "+APIcalls.inAPIcall );

                if (Utilities.animationName.equals("loading.json") && action.equals(APIcalls.RETRIEVE_PLAYERS) && APIcalls.inAPIcall == false){
                    Utilities.log(LOG_LOTTIE, "STARTING NEW ACTIVITY" );
                    Intent intent = new Intent(LottieResultActivity.this, PlayersActivity.class);
                    finish();
                    startActivity(intent);
                }
                else if (Utilities.animationName.equals("loading.json") && action.equals(APIcalls.RETRIEVE_FUTURE_MATCHES) && APIcalls.inAPIcall == false){
                    Utilities.log(LOG_LOTTIE, "STARTING NEW ACTIVITY" );
                    Intent intent = new Intent(LottieResultActivity.this, HomePageActivity.class);
                    finish();
                    HomePageActivity.setListViews();
                    startActivity(intent);
                }

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

        animationView.addAnimatorListener(animationListener);
        Utilities.log(LOG_LOTTIE, "Before api call, "+action.toString()+" animationName: "+Utilities.animationName + " inAPI: "+APIcalls.inAPIcall);
        APIcalls.doAction(action, animationView);
    }

    @Override
    public void onBackPressed() {
        //disable back press button to avoid canceling our activity logic
    }
}
