package com.example.smartcalendar.notes.listeners;

import com.example.smartcalendar.notes.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
