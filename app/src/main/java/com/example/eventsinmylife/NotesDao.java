package com.example.eventsinmylife;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes WHERE month = :idMonth + 1 ORDER BY 2")
    LiveData<List<Note>> getNotes(int idMonth);

    @Insert
    Completable add (Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    Completable  remove (int id);


}
