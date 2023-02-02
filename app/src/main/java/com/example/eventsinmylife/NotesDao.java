package com.example.eventsinmylife;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes WHERE month = :idMonth + 1")
    List<Note>  getNotes(int idMonth);

    @Insert
    void  add (Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void  remove (int id);


}
