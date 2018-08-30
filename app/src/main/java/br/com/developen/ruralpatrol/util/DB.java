package br.com.developen.ruralpatrol.util;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import br.com.developen.ruralpatrol.room.PlaceDAO;
import br.com.developen.ruralpatrol.room.PlaceVO;

@Database(entities = {PlaceVO.class}, version = 002, exportSchema = false)
public abstract class DB extends RoomDatabase {

    private static DB INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        public void migrate(SupportSQLiteDatabase database) {

            //database.execSQL("CREATE TABLE");

        }

    };

    public static DB getInstance(Context context) {

        if (INSTANCE == null) {

            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), DB.class, "ruralpatrol-database")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();

        }

        return INSTANCE;

    }

    public abstract PlaceDAO placeDAO();

}