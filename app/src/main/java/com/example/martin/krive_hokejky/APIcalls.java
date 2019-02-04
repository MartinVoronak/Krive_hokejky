package com.example.martin.krive_hokejky;

import com.airbnb.lottie.LottieAnimationView;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.martin.krive_hokejky.Activities.AddPlayerActivity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static com.example.martin.krive_hokejky.Constants.LOG_BACKANDLESS;

public enum APIcalls implements Serializable {

    CREATE, RETRIEVE;
    public static boolean inAPIcall = false;

    public static void changeStateAPI(){
        inAPIcall = !inAPIcall;
    }

    public static void doAction(APIcalls action, final LottieAnimationView animationView) {
        switch (action) {
            case CREATE:

                Utilities.log(Constants.LOG_BACKANDLESS, "API call Create player");
                changeStateAPI();

                animationView.loop(true);
                animationView.playAnimation();

                Player player = new Player();
                player.setFirstName(AddPlayerActivity.editName.getText().toString());
                player.setSurname(AddPlayerActivity.editSurname.getText().toString());

                //CREATE API
                Backendless.Data.of(Player.class).save(player,
                        new AsyncCallback() {

                            @Override
                            public void handleResponse(Object response) {
                                animationView.cancelAnimation();

                                Utilities.setAnimationName(animationView, "success.json");
                                animationView.loop(false);
                                animationView.playAnimation();
                                changeStateAPI();
                                Utilities.log(LOG_BACKANDLESS, "response: " + response);
                            }

                            // an error has occurred, the error code can be retrieved with fault.getCode()
                            @Override
                            public void handleFault(BackendlessFault fault) {
                                animationView.cancelAnimation();

                                Utilities.setAnimationName(animationView, "error.json");
                                animationView.loop(false);
                                animationView.playAnimation();
                                changeStateAPI();

                                Utilities.log(LOG_BACKANDLESS, "Server reported an error " + fault.getMessage());
                            }
                        });
                break;

            case RETRIEVE:

                changeStateAPI();
                Utilities.log(Constants.LOG_BACKANDLESS, "API call Retrieve players");

                animationView.loop(true);
                animationView.playAnimation();

                Backendless.Data.of(Player.class).find(new AsyncCallback<List<Player>>() {
                    @Override
                    public void handleResponse(List<Player> foundContacts) {
                        Utilities.log(LOG_BACKANDLESS, "response: classes loaded");
                        changeStateAPI();
                        animationView.cancelAnimation();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        animationView.cancelAnimation();

                        Utilities.setAnimationName(animationView, "error.json");
                        animationView.loop(false);
                        animationView.playAnimation();
                        changeStateAPI();
                        Utilities.log(LOG_BACKANDLESS, "Server reported an error: " + fault.getMessage());
                    }
                });

                break;

            default:
                System.out.println("Midweek days are so-so.");
                break;
        }
    }

}
