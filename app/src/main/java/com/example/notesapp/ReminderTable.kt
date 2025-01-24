package com.example.notesapp

import android.widget.EditText
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Reminder")
data class ReminderTable(

    var title:String,
    var hour:Int,
    var minutes:Int,
    var date:String,
    @PrimaryKey(autoGenerate = true)
    var id:Int=0

)
