package com.example.martin.krive_hokejky;

import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.io.Serializable;

import static com.example.martin.krive_hokejky.Constants.LOG_BACKANDLESS;

public enum APIcalls implements Serializable {

    CREATE();


    public static void doAction(APIcalls action, final LottieAnimationView animationView) {
        switch (action) {
            case CREATE:


                animationView.playAnimation();
                Player player = new Player();
                player.setFirstName(AddPlayerActivity.editName.getText().toString());
                player.setSurname(AddPlayerActivity.editSurname.getText().toString());

                Utilities.log(Constants.LOG_BACKANDLESS, "Create player API call");

                //REST API
                Backendless.Data.of(Player.class).save(player,
                    new AsyncCallback() {

                        @Override
                        public void handleResponse(Object response) {
                            animationView.cancelAnimation();

                            animationView.setAnimation("success.json");
                            animationView.loop(false);
                            animationView.playAnimation();
                            Utilities.setAnimationName("success.json");
                            Utilities.log(LOG_BACKANDLESS,  "response: "+response);
//                            ((Activity) context).finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            //animationView.setAnimation("HamburgerArrow.json");
                            Utilities.log(LOG_BACKANDLESS, "Server reported an error " + fault.getMessage());
                        }
                    });
                break;

            default:
                System.out.println("Midweek days are so-so.");
                break;
        }
    }

}
