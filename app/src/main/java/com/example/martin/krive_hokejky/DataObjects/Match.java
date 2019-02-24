package com.example.martin.krive_hokejky.DataObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Match implements Serializable {

    public String americaDateMatch;
    public String europeDateMatch;
    public List<Player> players = new ArrayList<>();
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

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player p){
        players.add(p);
    }

}
