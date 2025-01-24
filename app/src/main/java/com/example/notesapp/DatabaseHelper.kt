package com.example.notesapp

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [NotesTable::class,ReminderTable::class],
    exportSchema = false,
    version = 6
)

abstract class DatabaseHelper :RoomDatabase(){



    abstract fun notesdao() : NotesDao
    abstract fun reminderDao():ReminderDao


    companion object{
        val DB_NAME="notes_db"

        @Volatile
        private var INSTANCE : DatabaseHelper? = null


        fun getInstace(context: Context):DatabaseHelper{
             return INSTANCE?: synchronized(this){
                 val instance=Room.databaseBuilder(context.applicationContext,DatabaseHelper::class.java,
                     DB_NAME).fallbackToDestructiveMigration().build()
                 INSTANCE=instance
                 instance
             }

        }

    }
}