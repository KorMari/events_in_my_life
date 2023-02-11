package com.example.eventsinmylife;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNewViewModel extends AndroidViewModel {

private  NotesDao notesDao;
private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> shouldCloseToScreen = new MutableLiveData<>();


    public AddNewViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(getApplication()).notesDao();
    }

public  void saveNote (Note note){
    Disposable disposable = notesDao.add(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action() {
                @Override
                public void run() throws Throwable {
                    shouldCloseToScreen.setValue(true);

                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Throwable {
                    Log.d("AddNewNoteActivity", "Ошибка при добавлении заметки");
                }
            });
    compositeDisposable.add(disposable);
}

    public LiveData<Boolean> getShouldCloseToScreen() {
        return shouldCloseToScreen;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
