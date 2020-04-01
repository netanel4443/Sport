package com.e.Sport.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.Sport.R
import com.e.Sport.data.Exercise
import com.e.Sport.ui.recyclerviews.CheckBoxListAdapter
import kotlinx.android.synthetic.main.list_dlalog.view.*
import java.util.*
import kotlin.collections.HashSet

class ListDialog(val context: Context) {

    fun show(list: LinkedHashMap<String, Boolean>,checkedList:HashSet<String> ,onChecked:(String, Boolean)->Unit){
        val alertDialog= AlertDialog.Builder(context)
        val inflater= LayoutInflater.from(context)
        val view=inflater.inflate(R.layout.list_dlalog,null)
        val recyclerView=view.recyclerviewListDialog
        val adapter=CheckBoxListAdapter(list, checkedList ){ it:String, isChecked->
                onChecked(it,isChecked)
        }
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        recyclerView.setHasFixedSize(true)

        alertDialog.setView(view)
        alertDialog.create().show()
    }
}