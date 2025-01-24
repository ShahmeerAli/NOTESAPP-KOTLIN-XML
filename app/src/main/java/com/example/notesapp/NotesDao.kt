package com.example.notesapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface NotesDao {

    @Insert
    suspend fun insertData(note:NotesTable)

    @Query("select * from Notes")
   suspend fun getData(): MutableList<NotesTable>

    @Delete
    suspend fun deleteData(note:NotesTable)

    @Update
   suspend  fun UpdateData(note: NotesTable)




}