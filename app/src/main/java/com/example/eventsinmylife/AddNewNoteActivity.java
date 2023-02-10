package com.example.eventsinmylife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
private  AddNewViewModel addViewModel;
private int openMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        addViewModel = new ViewModelProvider(this).get(AddNewViewModel.class);
        addViewModel.getShouldCloseToScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose) {
                    Intent intent = MainActivity.newIntent(AddNewNoteActivity.this, openMonth);
                    intent.putExtra("numberOfMonth", openMonth - 1);
                    startActivity(intent);
                }
            }
        });
        initViews();
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
           openMonth = month;
           int  priority = getPriority();

            Note note = new Note( number, month, priority, personName);
addViewModel.saveNote(note);


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