package com.example.eventsinmylife;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private NotesDao notesDao;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(getApplication()).notesDao();
    }

    public  void remove (Note note){
       Disposable disposable = notesDao.remove(note.getId())
               .subscribeOn(Schedulers.io())
               .subscribe(new Action() {
                   @Override
                   public void run() throws Throwable {
                       Log.d(
                               "MainActivity",
                               "Удалено событие "
                                       + note.getDateOf()
                                       + note.getMonth()
                                       + note.getWhatEvent()
                                       + note.getPersonName());
                   }
               }, new Consumer<Throwable>() {
                   @Override
                   public void accept(Throwable throwable) throws Throwable {
                       Log.d(
                               "MainActivity",
                               "Ошибка в удалении события"
                                       + note.getDateOf()
                                       + note.getMonth()
                                       + note.getWhatEvent()
                                       + note.getPersonName());
                   }
               });
       compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public LiveData <List<Note>> getNotes (int idMonth){
return notesDao.getNotes(idMonth);
    }
}
