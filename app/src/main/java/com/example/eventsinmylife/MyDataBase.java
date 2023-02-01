package com.example.eventsinmylife;

import java.util.ArrayList;
import java.util.Random;

public class MyDataBase {
    ArrayList <Note> notes = new ArrayList<>();
    private static MyDataBase instance = null;

    public static MyDataBase getInstance(){
        if (instance == null){
            instance = new MyDataBase();
        }
        return instance;
    }

    private MyDataBase(){
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Note note = new Note(notes.size(), random.nextInt(31), random.nextInt(13), random.nextInt(3), "Name " + i);
            notes.add(note);
        }

    }

    public  void add (Note note){
        notes.add(note);
    }

    public void remove (int id){
        for (int i = 0; i < notes.size(); i++){
            Note note = notes.get(i);
            if (note.getId() ==id){
                notes.remove(note);
            }

        }

    }

    public ArrayList<Note> getNotes() {
        return new ArrayList<>(notes);
    }
}
