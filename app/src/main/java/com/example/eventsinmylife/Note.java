package com.example.eventsinmylife;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int dateOf;
    private int month;
    private int whatEvent;
    private String personName;


    public int getMonth() {
        return month;
    }

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDateOf() {
        return dateOf;
    }


    public int getWhatEvent() {
        return whatEvent;
    }



    public String getPersonName() {
        return personName;
    }



    public Note(int id, int dateOf, int month, int whatEvent, String personName) {
        this.id = id;
        this.dateOf = dateOf;
        this.month = month;
        this.whatEvent = whatEvent;
        this.personName = personName;
    }

@Ignore
    public Note( int dateOf, int month, int whatEvent, String personName) {
        this.dateOf = dateOf;
        this.month = month;
        this.whatEvent = whatEvent;
        this.personName = personName;
    }
}
