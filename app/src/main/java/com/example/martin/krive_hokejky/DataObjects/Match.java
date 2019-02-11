package com.example.martin.krive_hokejky.DataObjects;

import java.util.List;

public class Match {

    public String dateMatch;
    public String europeDateMatch;
    public List<Player> players;

    public Match(){
    }

    public String getDate() {
        return dateMatch;
    }

    public void setDateMatch(String date) {
        this.dateMatch = date;
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
