package com.example.eventsinmylife;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Note>  getNotes();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 1")
    List <Note> getJanuary ();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 2")
    List <Note> getFebruary ();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 3")
    List <Note> getMarch ();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 4")
    List <Note> getApril();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 5")
    List <Note> getMay ();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 6")
    List <Note> getJune ();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 7")
    List <Note> getJuly ();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 8")
    List <Note> getAugust ();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 9")
    List <Note> getSeptember ();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 10")
    List <Note> getOctober ();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 11")
    List <Note> getNovember ();

    @Query("SELECT id, dateOf, month, whatEvent, personName FROM notes WHERE month == 12")
    List <Note> getDecember ();


    @Insert
    void  add (Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void  remove (int id);


}
