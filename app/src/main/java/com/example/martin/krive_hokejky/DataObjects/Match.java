package com.example.martin.krive_hokejky.DataObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Match implements Serializable {

    public String americaDateMatch;
    public String europeDateMatch;
    public List<Player> players;
    public List<Player> unsignPlayers;
    public String objectId;

    public Match(){
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId( String objectId ) {
        this.objectId = objectId;
    }

    public String getAmericaDateMatch() {
        return americaDateMatch;
    }

    public void setAmericaDateMatch(String date) {
        this.americaDateMatch = date;
    }

    public String getEuropeDateMatch() {
        return europeDateMatch;
    }

    public void setEuropeDateMatch(String europeDate) {
        this.europeDateMatch = europeDate;
    }

    public void addPlayer(Player p){
        //from backendless example
        if( p == null ){
            players = new ArrayList<Player>();
        }

        players.add(p);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void unsignPlayer(Player p){
        this.unsignPlayers = new ArrayList<Player>();
        unsignPlayers.add(p);
    }

    public List<Player> getUnsignPlayers() {
        return unsignPlayers;
    }
}
