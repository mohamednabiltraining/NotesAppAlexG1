package com.route.NotesApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.route.NotesApp.DataBase.Model.Note;
import com.route.NotesApp.DataBase.MyDataBase;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NotesAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        adapter=new NotesAdapter(null);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton fab = findViewById(R.id.add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(HomeActivity.this,AddNoteActivity.class));
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note note =adapter.getNote(viewHolder.getAdapterPosition());
                deleteNote(note);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    public void deleteNote(final Note note){
        MyDataBase.getInstance(this)
                .notesDao()
                .deleteNote(note);
        refreshRecyclerView();
        Snackbar.make(findViewById(R.id.container),
                "Do you want to undo ?", 10000)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        restoreNote(note);
                    }
                }).show();

    }

    private void restoreNote(Note note) {
        MyDataBase.getInstance(this)
                .notesDao()
                .addNote(note);
        refreshRecyclerView();
    }

    public void refreshRecyclerView(){
        List<Note> notesList= MyDataBase.getInstance(this)
                .notesDao()
                .getAllNotes();
        adapter.updateData(notesList);
    }
    /*        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                           .setAction("Action", null).show();
        */
    @Override
    protected void onStart() {
        super.onStart();
        refreshRecyclerView();

    }
}
