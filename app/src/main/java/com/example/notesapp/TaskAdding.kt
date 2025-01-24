package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskAdding : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_adding)

        val Edtitle: EditText? = findViewById(R.id.EdTitle)
        val Edcontent: EditText? = findViewById(R.id.EdContent)
        val AddBt: Button? = findViewById(R.id.SaveBt)

        val backImg: ImageView = findViewById(R.id.backimage)
        val databaseHelper = DatabaseHelper.getInstace(this)

        backImg.setOnClickListener {
            onBackPressed()
        }





        AddBt?.setOnClickListener {
            val title = Edtitle?.text.toString()
            val desc = Edcontent?.text.toString()
            if (desc.isBlank()) {
                Toast.makeText(
                    this,
                    "Content cannot be Empty! \n Write something before saving. ",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                CoroutineScope(Dispatchers.IO).launch {
                    databaseHelper.notesdao().insertData(NotesTable(title, desc))
                    runOnUiThread {
                        Toast.makeText(this@TaskAdding, "Note Saved", Toast.LENGTH_SHORT).show()
                    }
                    startActivity(Intent(this@TaskAdding, MainActivity::class.java))
                    finish()


                }


            }


        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@TaskAdding, MainActivity::class.java))
        finish()
    }




}




