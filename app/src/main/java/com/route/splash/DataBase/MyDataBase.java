package com.route.splash.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.route.splash.DataBase.DAOs.NotesDao;
import com.route.splash.DataBase.Model.Note;

/**
 * Created by Mohamed Nabil Mohamed on 9/14/2019.
 * m.nabil.fci2015@gmail.com
 */
@Database(entities = {Note.class},version = 1,exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase dataBase;
    private static final String DB_NAME= "NotesDatabase";

    public abstract NotesDao notesDao();

    private MyDataBase(){

    }
    public static MyDataBase getInstance(Context context){
        if(dataBase==null){
            //create database
            dataBase =  Room.databaseBuilder(context,MyDataBase.class,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return dataBase;
    }
}
