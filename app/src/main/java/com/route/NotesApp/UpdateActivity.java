package com.route.NotesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.route.NotesApp.DataBase.Model.Note;
import com.route.NotesApp.DataBase.MyDataBase;

import java.io.Serializable;
import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {
    Note oldNote;
    Note note;
    Gson gson = new Gson();


    protected EditText title;
    protected EditText content;
    protected TextView datetime;
    protected TextView update;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title = findViewById(R.id.update_title);
        content =  findViewById(R.id.update_content);
        datetime = findViewById(R.id.update_datetime);
        update = findViewById(R.id.save_update);


        note =  gson.fromJson(getIntent().getStringExtra("myjosn"),Note.class) ;
        String titleS = note.getTitle();
        title.setText(titleS);
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
