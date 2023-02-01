package com.example.eventsinmylife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewMonth;
    private ImageView imageViewOneBack;
    private ImageView imageViewOneForward;
    private ImageView imageViewFirstMonth;
    private ImageView imageViewEndMonth;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerViewForNotes;
    private NotesAdapter notesAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private NoteDatabase noteDatabase;
    private String[] months;
    private int numberOfMonth;

    public int getNumberOfMonth() {
        return numberOfMonth;
    }

    public void setNumberOfMonth(int numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            setNumberOfMonth(savedInstanceState.getInt("numberOfMonth"));

        } else {
            setNumberOfMonth(0);
        }
        setContentView(R.layout.activity_main);
        initViews();
        noteDatabase = NoteDatabase.getInstance(getApplication());
        notesAdapter = new NotesAdapter();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                Note note = notesAdapter.getNotes().get(position);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        noteDatabase.notesDao().remove(note.getId());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                changeMonths();
                            }
                        });
                    }
                });
thread.start();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewForNotes);
        recyclerViewForNotes.setAdapter(notesAdapter);
        months = getResources().getStringArray(R.array.months);




        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddNewNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });


        imageViewOneBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getNumberOfMonth() > 0) {
                    setNumberOfMonth(getNumberOfMonth() - 1);
                    changeMonths();
                } else {
                    changeMonths();
                }

            }
        });

        imageViewOneForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getNumberOfMonth() < 11) {
                    setNumberOfMonth(getNumberOfMonth() + 1);
                    changeMonths();
                } else {
                    changeMonths();
                }

            }
        });

        imageViewFirstMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNumberOfMonth(0);
                changeMonths();
            }
        });

        imageViewEndMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNumberOfMonth(11);
                changeMonths();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeMonths();
    }

    private void initViews() {
        textViewMonth = findViewById(R.id.textViewMonth);
        imageViewOneBack = findViewById(R.id.imageViewOneBack);
        imageViewOneForward = findViewById(R.id.imageViewOneForward);
        imageViewFirstMonth = findViewById(R.id.imageViewFirstMonth);
        imageViewEndMonth = findViewById(R.id.imageViewEndMonth);
        recyclerViewForNotes = findViewById(R.id.recyclerViewForNotes);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }


    private void changeMonths() {
        setNumberOfMonth(getNumberOfMonth());
        textViewMonth.setText(months[numberOfMonth]);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Note> notesDependenceFromMonth;
                switch (numberOfMonth) {
                    case 0:
                        notesDependenceFromMonth = noteDatabase.notesDao().getJanuary();
                        break;
                    case 1:
                        notesDependenceFromMonth = noteDatabase.notesDao().getFebruary();
                        break;
                    case 2:
                        notesDependenceFromMonth = noteDatabase.notesDao().getMarch();
                        break;
                    case 3:
                        notesDependenceFromMonth = noteDatabase.notesDao().getApril();
                        break;
                    case 4:
                        notesDependenceFromMonth = noteDatabase.notesDao().getMay();
                        break;
                    case 5:
                        notesDependenceFromMonth = noteDatabase.notesDao().getJune();
                        break;
                    case 6:
                        notesDependenceFromMonth = noteDatabase.notesDao().getJuly();
                        break;
                    case 7:
                        notesDependenceFromMonth = noteDatabase.notesDao().getAugust();
                        break;
                    case 8:
                        notesDependenceFromMonth = noteDatabase.notesDao().getSeptember();
                        break;
                    case 9:
                        notesDependenceFromMonth = noteDatabase.notesDao().getOctober();
                        break;
                    case 10:
                        notesDependenceFromMonth = noteDatabase.notesDao().getNovember();
                        break;
                    case 11:
                        notesDependenceFromMonth = noteDatabase.notesDao().getDecember();
                        break;
                    default:
                        notesDependenceFromMonth = noteDatabase.notesDao().getJanuary();
                        break;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notesAdapter.setNotes(notesDependenceFromMonth);
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("numberOfMonth", getNumberOfMonth());
    }
}
