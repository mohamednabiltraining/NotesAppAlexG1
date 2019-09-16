package com.route.NotesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {
    TextView noteContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteContent= findViewById(R.id.note_content);
        String content = getIntent().getStringExtra("content");
        noteContent.setText(content);
    }
}
