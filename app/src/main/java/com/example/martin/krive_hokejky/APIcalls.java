package com.example.martin.krive_hokejky;

import com.airbnb.lottie.LottieAnimationView;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.martin.krive_hokejky.Activities.AddMatchActivity;
import com.example.martin.krive_hokejky.Activities.AddPlayerActivity;
import com.example.martin.krive_hokejky.DataObjects.Match;
import com.example.martin.krive_hokejky.DataObjects.Player;

import java.io.Serializable;
import java.util.List;

import static com.example.martin.krive_hokejky.Constants.LOG_BACKANDLESS;

public enum APIcalls implements Serializable {

    CREATE_PLAYER, CREATE_MATCH, RETRIEVE_PLAYERS;
    public static boolean inAPIcall = false;

    public static void changeStateAPI(){
        inAPIcall = !inAPIcall;
    }

    public static List<Player> players;


    public static void doAction(APIcalls action, final LottieAnimationView animationView) {
        switch (action) {
            case CREATE_PLAYER:

                Utilities.log(Constants.LOG_BACKANDLESS, "API call Create player");
                changeStateAPI();

                animationView.loop(true);
                animationView.playAnimation();

                final Player player = new Player();
                player.setFirstName(AddPlayerActivity.editName.getText().toString());
                player.setSurname(AddPlayerActivity.editSurname.getText().toString());

                //CREATE_PLAYER API
                Backendless.Data.of(Player.class).save(player,
                        new AsyncCallback<Player>() {

                            @Override
                            public void handleResponse(Player response) {
                                responseOK(animationView, ""+response);
                            }

                            // an error has occurred, the error code can be retrieved with fault.getCode()
                            @Override
                            public void handleFault(BackendlessFault fault) {
                                responseNotOK(animationView, fault.getMessage());
                            }
                        });
                break;

            case RETRIEVE_PLAYERS:

                changeStateAPI();
                Utilities.log(Constants.LOG_BACKANDLESS, "API call Retrieve players");

                animationView.loop(true);
                animationView.playAnimation();

                Backendless.Data.of(Player.class).find(new AsyncCallback<List<Player>>() {
                    @Override
                    public void handleResponse(List<Player> foundContacts) {
                        players = foundContacts;

                        Utilities.log(LOG_BACKANDLESS, "response: classes loaded");
                        changeStateAPI();
                        animationView.cancelAnimation();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        responseNotOK(animationView, fault.getMessage());
                    }
                });
                break;

            case CREATE_MATCH:

                changeStateAPI();
                Utilities.log(Constants.LOG_BACKANDLESS, "API call Create match");
                animationView.loop(true);
                animationView.playAnimation();

                //fetch matches from activity list
                List<Match> matchesToSend = AddMatchActivity.futureMatches;
                if (!matchesToSend.isEmpty()){

                    Backendless.Data.of( Match.class ).create( matchesToSend, new AsyncCallback<List<String>>() {
                        @Override
                        public void handleResponse(List<String> response) {
                            responseOK(animationView, ""+response);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            responseNotOK(animationView, fault.getMessage());
                        }
                    });
                }
                break;

            default:
                System.out.println("Midweek days are so-so.");
                break;
        }
    }

    public static void responseOK(LottieAnimationView animationView, String response){
        animationView.cancelAnimation();

        Utilities.setAnimationName(animationView, "success.json");
        animationView.loop(false);
        animationView.playAnimation();
        changeStateAPI();
        Utilities.log(LOG_BACKANDLESS, "response: " + response);
    }

    public static void responseNotOK(LottieAnimationView animationView, String response){
        animationView.cancelAnimation();

        Utilities.setAnimationName(animationView, "error.json");
        animationView.loop(false);
        animationView.playAnimation();
        changeStateAPI();

        Utilities.log(LOG_BACKANDLESS, "Server reported an error " + response);
    }

}
