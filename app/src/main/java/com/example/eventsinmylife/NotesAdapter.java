package com.example.eventsinmylife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    List<Note> notes = new ArrayList<>();

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);

        return new NotesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NotesViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        viewHolder.textViewNumber.setText(String.valueOf(note.getDateOf()));

        int event = note.getWhatEvent();
        String eventText;
//        int colorRes;
        switch (event) {
            case 0:
                eventText = "ДР";
//                colorRes = R.color.red;
                break;
            case 1:
                eventText = "ДП";
//                colorRes = R.color.gray;
                break;
            default:
                eventText = "ГС";
//                colorRes = R.color.sunshain;
                break;
        }
//        устанавливает цвет фона у события
//        viewHolder.textViewEvent.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), colorRes));
        viewHolder.textViewEvent.setText(eventText);
        viewHolder.textViewPersonName.setText(note.getPersonName());


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNumber;
        private TextView textViewEvent;
        private TextView textViewPersonName;


        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewEvent = itemView.findViewById(R.id.textViewEvent);
            textViewPersonName = itemView.findViewById(R.id.textViewPersonName);


        }
    }


}
