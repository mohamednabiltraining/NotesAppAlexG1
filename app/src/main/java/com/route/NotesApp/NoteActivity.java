package com.route.NotesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.route.NotesApp.DataBase.Model.Note;

import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {
    Gson gson= new Gson();

    TextView noteContent;
    TextView update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        update = findViewById(R.id.update);
        noteContent= findViewById(R.id.note_content);

        final Note note  =gson.fromJson(getIntent().getStringExtra("myjson"),Note.class);
        String content = note.getContent();

        noteContent.setText(content);
        // start update activity
       update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(NoteActivity.this, UpdateActivity.class);

               intent.putExtra("myjosn",  gson.toJson(note));

               startActivity(intent);

           }
       });
    }
}
