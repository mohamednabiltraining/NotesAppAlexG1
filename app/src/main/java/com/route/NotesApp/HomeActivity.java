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

import java.util.ArrayList;
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
        /*        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
     */
            startActivity(new Intent(HomeActivity.this,AddNoteActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

       final ArrayList<Note>  notesList = (ArrayList<Note>) getnotes();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                //delete item
                final Note noteToDelte = adapter.getNote(viewHolder.getAdapterPosition());
                MyDataBase.getInstance(getApplication()).notesDao().deleteNote(noteToDelte);
                notesList.remove(noteToDelte);
                adapter.updateData(notesList);

                Snackbar.make(findViewById(R.id.rootLayout),"note deleted",Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MyDataBase.getInstance(getApplication()).notesDao().addNote(noteToDelte);
                                adapter.updateData(getnotes());
                            }
                        }).show();

            }
        }).attachToRecyclerView(recyclerView);




        adapter.updateData(notesList);
    }

    public List<Note> getnotes(){
        List<Note> notesList= MyDataBase.getInstance(this)
                .notesDao()
                .getAllNotes();

        return notesList;
    }
}
