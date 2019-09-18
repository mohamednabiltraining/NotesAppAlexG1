package com.route.NotesApp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.route.NotesApp.DataBase.Model.Note;

import java.util.List;

/**
 * Created by Mohamed Nabil Mohamed on 9/14/2019.
 * m.nabil.fci2015@gmail.com
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    List<Note> notes ;


    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.time.setText(note.getDateTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position);
            }
        });

    }

    public void updateData(List<Note>notes){
        this.notes =notes;
        notifyDataSetChanged();
    }

    public Note getNote(int pos){
        return notes.get(pos);
    }

    @Override
    public int getItemCount() {
        if(notes==null)
            return 0;
        return notes.size();
    }
    onClickListener onClickListener;

    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public  interface onClickListener {
        void onClick(int pos);
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            time=itemView.findViewById(R.id.time);

        }
    }
}
