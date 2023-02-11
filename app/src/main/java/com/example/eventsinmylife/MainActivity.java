package com.example.eventsinmylife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
    private String[] months;
    private int numberOfMonth;
    private  MainViewModel mainViewModel;

    public int getNumberOfMonth() {
        return numberOfMonth;
    }

    public void setNumberOfMonth(int numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        Intent intent = getIntent();
        int month = intent.getIntExtra("numberOfMonth",0);

        if (savedInstanceState != null) {
            setNumberOfMonth(savedInstanceState.getInt("numberOfMonth"));

        } else {
            setNumberOfMonth(month);
        }
        setContentView(R.layout.activity_main);
        initViews();
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
mainViewModel.remove(note);
                                changeMonths();

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
                }

            }
        });

        imageViewOneForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getNumberOfMonth() < 11) {
                    setNumberOfMonth(getNumberOfMonth() + 1);
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

        textViewMonth.setText(months[getNumberOfMonth()]);

                mainViewModel.getNotes(getNumberOfMonth())
                        .observe(MainActivity.this, new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                        notesAdapter.setNotes(notes);
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("numberOfMonth", getNumberOfMonth());
    }

    public static Intent newIntent (Context context, int numberOfMonth){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("numberOfMonth", numberOfMonth);
        return intent;
    }
}
