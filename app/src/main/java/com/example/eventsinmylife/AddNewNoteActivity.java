package com.example.eventsinmylife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class AddNewNoteActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private EditText editTextMonth;
    private  EditText editTextPersonName;
    private RadioButton radioButtonPriorityBirthday;
    private RadioButton radioButtonPriorityDeath;
    private Button buttonSave;
    private NoteDatabase noteDatabase;
private Handler handler = new Handler(Looper.getMainLooper());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        initViews();
        noteDatabase = NoteDatabase.getInstance(getApplication());
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddNewNoteActivity.class);
        return intent;
    }

    public void initViews() {
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextMonth = findViewById(R.id.editTextMonth);
        editTextPersonName = findViewById(R.id.editTextPersonName);
        radioButtonPriorityBirthday = findViewById(R.id.radioButtonPriorityBirthday);
        radioButtonPriorityDeath = findViewById(R.id.radioButtonPriorityDeath);
        buttonSave = findViewById(R.id.buttonSave);
    }

    private void saveNote() {
        String numberOfDay = editTextNumber.getText().toString().trim();
        String numberOfMonth = editTextMonth.getText().toString().trim();
        String personName = editTextPersonName.getText().toString().trim();

        if (numberOfDay.isEmpty() || numberOfMonth.isEmpty() || personName.isEmpty()) {
            Toast.makeText(this, R.string.must_be_filled, Toast.LENGTH_SHORT).show();
        } else {
            String date = "2020-"  + numberOfMonth + "-" + numberOfDay;

            try {
                LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
            } catch (Exception e) {
                Toast.makeText(this, R.string.does_not_exist, Toast.LENGTH_SHORT).show();
                return;
            }

            int number = Integer.parseInt(numberOfDay);
           int  month = Integer.parseInt(numberOfMonth);
           int  priority = getPriority();

            Note note = new Note( number, month, priority, personName);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    noteDatabase.notesDao().add(note);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
//                            finish();
                            Intent intent = MainActivity.newIntent(AddNewNoteActivity.this, month);
                            intent.putExtra("numberOfMonth", month - 1);
                            startActivity(intent);
                        }
                    });
                }
            });
            thread.start();

        }
    }


    private int getPriority() {
        int priority;
        if (radioButtonPriorityBirthday.isChecked()) {
            priority = 0;
        } else if (radioButtonPriorityDeath.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }
}