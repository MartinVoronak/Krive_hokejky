package com.example.martin.krive_hokejky;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;

public class LottieResult extends AppCompatActivity {

    //TODO remove activity from stack, check SMS app
    public static LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_result);

        APIcalls action = (APIcalls) getIntent().getSerializableExtra("action");

        animationView = (LottieAnimationView) findViewById(R.id.animation_load);
        animationView.setAnimation("loading.json");


        Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                String name = animationView.getTransitionName();
                Log.i(Constants.LOG_LOTTIE, "end: "+name );
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (Utilities.animationName.equals("success.json")){
                    Intent intent = new Intent(LottieResult.this, HomePage.class);
                    finish();
                    startActivity(intent);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

        animationView.addAnimatorListener(animationListener);
        APIcalls.doAction(action, animationView);
    }
}
