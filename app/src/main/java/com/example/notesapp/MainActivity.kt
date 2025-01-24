package com.example.notesapp

import android.app.AlarmManager
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(){


    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesDao: NotesDao
    private var edremindertitle: String= null.toString()
    private  var  hours: Int =0
    private var minutes:Int=0
    private var dateSelected: String = null.toString()
    var counter = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        createNotificationChannel()


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //FINDING THE IDS

        val textViewWelcome = binding.textView
        val linearLayout = binding.LinearLayoutEmpty
        recyclerView = binding.RecyclerViewMain



        databaseHelper = DatabaseHelper.getInstace(this)


        recyclerView.layoutManager = GridLayoutManager(this, 2)


        showNotes()




        binding.AddButtonMain.setOnClickListener {
            startActivity(Intent(this@MainActivity, TaskAdding::class.java))
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right)


        }

       



        onBackPressed()


    }


    fun showNotes() {

        CoroutineScope(Dispatchers.Main).launch {
            val listNotes: MutableList<NotesTable> = databaseHelper.notesdao().getData()



            withContext(Dispatchers.Main) {
                if (listNotes.isEmpty()) {
                    binding.RecyclerViewMain.visibility = View.GONE
                    binding.LinearLayoutEmpty.visibility = View.VISIBLE
                } else {
                    binding.LinearLayoutEmpty.visibility = View.GONE
                    binding.RecyclerViewMain.visibility = View.VISIBLE
                    recyclerView.adapter = RecyclerAdapter(this@MainActivity, listNotes)
                }
            }

        }
    }

    override fun onBackPressed() {

        if (counter == 1) {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
        }

        if (counter == 2) {
            super.onBackPressed()
            finishAffinity()
        }
        counter++


    }



    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Reminder",
                "Reminder_Notes",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
    


}
