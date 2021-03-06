package com.example.martin.krive_hokejky;

import com.airbnb.lottie.LottieAnimationView;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.martin.krive_hokejky.Activities.AddMatchActivity;
import com.example.martin.krive_hokejky.Activities.AddPlayerActivity;
import com.example.martin.krive_hokejky.Activities.HomePageActivity;
import com.example.martin.krive_hokejky.Activities.SignMatch;
import com.example.martin.krive_hokejky.DataObjects.Match;
import com.example.martin.krive_hokejky.DataObjects.Player;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.martin.krive_hokejky.Constants.LOG_BACKANDLESS;

public enum APIcalls implements Serializable {

    CREATE_PLAYER, CREATE_MATCH, RETRIEVE_PLAYERS, RETRIEVE_FUTURE_MATCHES, SIGN_FUTURE_MATCH, UNSIGN_FUTURE_MATCH;

    public static boolean inAPIcall = false;
    public static List<Player> players;
    public static List<Match> matches;

    public static void changeStateAPI() {
        inAPIcall = !inAPIcall;
    }

    public static void doAction(APIcalls action, final LottieAnimationView animationView) {
        switch (action) {

            case CREATE_PLAYER:

                changeStateAPI();
                Utilities.log(Constants.LOG_BACKANDLESS, "API call Create player");

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
                                HomePageActivity.firstLoad = true;
                                responseOK(animationView, "" + response);
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

                        Utilities.log(LOG_BACKANDLESS, "response: players loaded");
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
                if (!matchesToSend.isEmpty()) {

                    Backendless.Data.of(Match.class).create(matchesToSend, new AsyncCallback<List<String>>() {
                        @Override
                        public void handleResponse(List<String> response) {
                            HomePageActivity.firstLoad = true;
                            responseOK(animationView, "" + response);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            responseNotOK(animationView, fault.getMessage());
                        }
                    });
                }
                break;

            case RETRIEVE_FUTURE_MATCHES:

                changeStateAPI();
                Utilities.log(Constants.LOG_BACKANDLESS, "API call Retrieve matches");
                animationView.loop(true);
                animationView.playAnimation();

                Backendless.Data.of(Player.class).find(new AsyncCallback<List<Player>>() {
                    @Override
                    public void handleResponse(List<Player> foundContacts) {
                        Utilities.log(LOG_BACKANDLESS, "PLAYERS");
                        players = foundContacts;
                        Utilities.log(LOG_BACKANDLESS, "response: players loaded");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        responseNotOK(animationView, fault.getMessage());
                        return;
                    }
                });

                Date now = new Date();
                String myFormat = "MM.dd.yyyy HH:mm:ss";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.GERMANY);
                String dateToCompare = dateFormat.format(now);

                String whereClause = "dateMatch > '" + dateToCompare + "'";
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                queryBuilder.setWhereClause(whereClause);

                Backendless.Data.of(Match.class).find(queryBuilder,
                        new AsyncCallback<List<Match>>() {
                            @Override
                            public void handleResponse(List<Match> foundMatches) {
                                Utilities.log(LOG_BACKANDLESS, "MATCHES");
                                matches = foundMatches;

                                Utilities.log(LOG_BACKANDLESS, "response: matches loaded");
                                changeStateAPI();
                                animationView.cancelAnimation();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                responseNotOK(animationView, fault.getMessage());
                            }
                        });

                break;

            case SIGN_FUTURE_MATCH:

                changeStateAPI();
                Utilities.log(Constants.LOG_BACKANDLESS, "API call Sign to match");
                animationView.loop(true);
                animationView.playAnimation();

//                //fetch matches from activity list
                Match signMatch = SignMatch.selectedMatch;
                Utilities.log(Constants.LOG_BACKANDLESS, "match id: "+signMatch.getObjectId());


                //relation: update TABLE Match - column players -> add class Player
                //backendless documentation: Add Relation with objects
                Backendless.Data.of(Match.class).addRelation(signMatch, "players:Player:n", signMatch.getPlayers(),
                        new AsyncCallback<Integer>() {
                            @Override
                            public void handleResponse(Integer response) {
                                HomePageActivity.firstLoad = true;
                                responseOK(animationView, "" + response);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                responseNotOK(animationView, fault.getMessage());
                            }
                        });
                break;

            case UNSIGN_FUTURE_MATCH:

                changeStateAPI();
                Utilities.log(Constants.LOG_BACKANDLESS, "API call Unsign to match");
                animationView.loop(true);
                animationView.playAnimation();

                //fetch match from activity list
                Match unsignMatch = SignMatch.selectedMatch;
                Utilities.log(Constants.LOG_BACKANDLESS, "match id: "+unsignMatch.getObjectId());

                Backendless.Data.of(Match.class).deleteRelation(unsignMatch, "players", unsignMatch.getUnsignPlayers(),
                        new AsyncCallback<Integer>() {
                            @Override
                            public void handleResponse(Integer response) {
                                HomePageActivity.firstLoad = true;
                                responseOK(animationView, "" + response);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                responseNotOK(animationView, fault.getMessage());
                            }
                        });
                break;

            default:
                System.out.println("Midweek days are so-so.");
                break;
        }
    }

    public static void responseOK(LottieAnimationView animationView, String response) {
        animationView.cancelAnimation();

        Utilities.setAnimationName(animationView, "success.json");
        animationView.loop(false);
        animationView.playAnimation();
        changeStateAPI();
        Utilities.log(LOG_BACKANDLESS, "response: " + response);
    }

    public static void responseNotOK(LottieAnimationView animationView, String response) {
        animationView.cancelAnimation();

        Utilities.setAnimationName(animationView, "error.json");
        animationView.loop(false);
        animationView.playAnimation();
        changeStateAPI();

        Utilities.log(LOG_BACKANDLESS, "Server reported an error " + response);
    }

}
