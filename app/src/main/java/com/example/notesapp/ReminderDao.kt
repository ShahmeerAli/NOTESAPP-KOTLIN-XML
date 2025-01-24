package com.example.notesapp

import androidx.room.Dao
import androidx.room.Insert
@Dao
interface ReminderDao {
    @Insert
    suspend fun setReminder(reminder: ReminderTable)
}