package com.zoomx.zoomx.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

import com.zoomx.zoomx.db.converters.DateConverter
import com.zoomx.zoomx.model.RequestEntity

/**
 * Created by Ahmed Fathallah on 12/6/2017.
 */
@Database(entities = arrayOf(RequestEntity::class), version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase internal constructor() : RoomDatabase() {

    abstract fun requestDao(): RequestDao

    companion object {

        private val DB_NAME = "ZoomXDB"
        private var database: AppDatabase? = null

        operator fun get(context: Context): AppDatabase? {
            synchronized(AppDatabase::class.java) {
                if (database == null) {
                    database = buildDataBase(context)
                }
            }
            return database
        }

        private fun buildDataBase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .build()
        }
    }

}
