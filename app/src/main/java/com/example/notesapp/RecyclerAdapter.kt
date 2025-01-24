package com.example.notesapp


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.timerTask

class RecyclerAdapter(var context: Context, var list: MutableList<NotesTable>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val items =
            LayoutInflater.from(parent.context).inflate(R.layout.add_notes_recycler, parent, false)



        return ViewHolder(items)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.title.setText(list[position].title)
        holder.desc.setText(list[position].desc)
        val currentpos = holder.adapterPosition



        holder.cardview.setOnClickListener {
            val dialgo = Dialog(context)
            dialgo.setContentView(R.layout.update_recycler)
            val edtitle = dialgo.findViewById<EditText>(R.id.EdRecyclerUpdate)
            val eddesc: EditText = dialgo.findViewById(R.id.EdContentRecyclerUpdate)
            val saveBt: Button = dialgo.findViewById(R.id.SaveBtUpdate)


            val pos = list[position]

            edtitle.setText(pos.title)
            eddesc.setText(pos.desc)

            saveBt.setOnClickListener {
                val updatedtitle = edtitle.text.toString()
                val updateddesc = eddesc.text.toString()
                val updateNote =
                    (NotesTable(updatedtitle, updateddesc, list[holder.adapterPosition].id))

                CoroutineScope(Dispatchers.IO).launch {
                    val databaseHelper = DatabaseHelper.getInstace(context)
                    databaseHelper.notesdao().UpdateData(updateNote)
                    withContext(Dispatchers.Main) {
                        list[position] = updateNote
                        notifyItemChanged(position)
                        dialgo.dismiss()
                        Toast.makeText(context, "Note updated successfully!", Toast.LENGTH_SHORT)
                            .show()


                    }
                }


            }
            dialgo.show()


        }


        val databaseHelper = DatabaseHelper.getInstace(context)
        holder.cardview.setOnLongClickListener(object : View.OnLongClickListener {

            override fun onLongClick(v: View?): Boolean {

                val alertBox = AlertDialog.Builder(context)
                alertBox.setTitle("Delete Note")
                alertBox.setMessage("Are you sure you want to delete?")
                alertBox.setIcon(R.drawable.delete_svgrepo_com)

                try {
                    alertBox.setPositiveButton(
                        "Yes",
                        DialogInterface.OnClickListener(
                            { dialog, which ->
                            try {

                                val postiondeleted = list[holder.adapterPosition]
                                CoroutineScope(Dispatchers.Main).launch {

                                    databaseHelper.notesdao().deleteData(
                                        NotesTable(
                                            postiondeleted.title,
                                            postiondeleted.desc,
                                            postiondeleted.id
                                        )
                                    )
                                    withContext(Dispatchers.Main) {
                                        if(currentpos>=0 && list.size>currentpos) {
                                            list.removeAt(currentpos)
                                            notifyItemRemoved(currentpos)
                                            notifyItemRangeChanged(
                                                currentpos,
                                                list.size - currentpos
                                            )
                                        }else{
                                            Log.e("Deletion Error","Invalid Position to delete : ${currentpos}")
                                        }

                                    }


                                    Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT)
                                        .show()


                                }


                            }catch (e:Exception){
                                Log.e("Error","Error while delettion",e)
                            }
                            })

                    )
                        .setNegativeButton(
                            "No",
                            DialogInterface.OnClickListener({ dialog, which ->

                            })
                        )

                    alertBox.show()
                } catch (e: Exception) {
                    Log.e("Deletion Exception", "Error while deleting")
                }

                return true
            }
        })


    }


    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title: TextView = item.findViewById(R.id.EdRecycler)
        val desc: TextView = item.findViewById(R.id.EdRecyclerContent)
        val cardview: CardView = item.findViewById(R.id.recyclerCardView)


    }


}