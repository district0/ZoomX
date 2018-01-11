package com.zoomx.zoomx.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.zoomx.zoomx.db.converters.DateConverter;
import com.zoomx.zoomx.model.RequestEntity;

/**
 * Created by Ahmed Fathallah on 12/6/2017.
 */
@Database(entities = {RequestEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "ZoomXDB";
    private static AppDatabase database;

    AppDatabase() {
    }

    public static AppDatabase get(Context context) {
        synchronized (AppDatabase.class) {
            if (database == null) {
                database = buildDataBase(context);
            }
        }
        return database;
    }

    private static AppDatabase buildDataBase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .build();
    }

    public abstract RequestDao requestDao();

}
