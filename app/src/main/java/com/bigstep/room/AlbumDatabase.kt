package com.bigstep.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bigstep.model.Album


@Database(entities = [Album::class], version = 4, exportSchema = false)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun albumDao() : DAOAccess

    companion object {

        @Volatile
        private var INSTANCE: AlbumDatabase? = null

        fun getDatabaseClient(context: Context) : AlbumDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, AlbumDatabase::class.java, "ALBUM_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}