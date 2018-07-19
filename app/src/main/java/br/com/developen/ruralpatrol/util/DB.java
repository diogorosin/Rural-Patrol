package br.com.developen.ruralpatrol.util;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.developen.ruralpatrol.room.PlaceDAO;
import br.com.developen.ruralpatrol.room.PlaceVO;

@Database(entities = {PlaceVO.class}, version = 001, exportSchema = false)
public abstract class DB extends RoomDatabase {

    private static DB INSTANCE;

    public static DB getInstance(Context context) {

        if (INSTANCE == null) {

            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), DB.class, "ruralpatrol-database")
                    .allowMainThreadQueries()
                    .build();

        }

        return INSTANCE;

    }

    public abstract PlaceDAO placeDAO();

}