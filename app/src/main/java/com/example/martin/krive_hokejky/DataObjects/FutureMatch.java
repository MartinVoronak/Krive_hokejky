package com.example.martin.krive_hokejky.DataObjects;

import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

public class FutureMatch {

    public String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEuropeDate() {
        return europeDate;
    }

    public void setEuropeDate(String europeDate) {
        this.europeDate = europeDate;
    }

    public String europeDate;

    public FutureMatch(){
    }

    //TODO maybe list of players
    public String getStringDate() {
        return date;
    }

    public void setStringDate(String date) {
        this.date = date;
    }

}
