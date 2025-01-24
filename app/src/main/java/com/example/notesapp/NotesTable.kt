package com.example.notesapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class NotesTable(

    @ColumnInfo(name = "Title")
    var title:String,

    @ColumnInfo(name = "Description")
    var desc:String,

    @PrimaryKey(autoGenerate = true)
    var id:Int=0

)


