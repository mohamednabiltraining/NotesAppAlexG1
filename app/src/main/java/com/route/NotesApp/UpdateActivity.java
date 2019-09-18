package com.route.NotesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.route.NotesApp.DataBase.Model.Note;
import com.route.NotesApp.DataBase.MyDataBase;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {
    ArrayList<String> oldNote;
    Note note;


    protected EditText title;
    protected EditText content;
    protected TextView datetime;
    protected Button update;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title = findViewById(R.id.title);
        content =  findViewById(R.id.note_content);
        datetime = findViewById(R.id.datetime);
        update = findViewById(R.id.update);


        oldNote = getIntent().getStringArrayListExtra("note");
        note.setTitle(oldNote.get(0));
        note.setContent(oldNote.get(1));
        note.setDateTime(oldNote.get(2));
       note.setId(getIntent().getIntExtra("noteId",-1));

       title.setText(note.getTitle());
       content.setText(note.getContent());
       datetime.setText(note.getDateTime());

       update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               note.setTitle(title.getText().toString());
               note.setContent(content.getText().toString());
               note.setDateTime(datetime.getText().toString());

               MyDataBase.getInstance(getApplication()).notesDao().updateNote(note);

               Intent intent = new Intent(UpdateActivity.this,HomeActivity.class);
               startActivity(intent);
           }
       });


    }
}
