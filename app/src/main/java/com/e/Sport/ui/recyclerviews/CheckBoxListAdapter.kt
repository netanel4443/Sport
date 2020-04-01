package com.e.Sport.ui.recyclerviews

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.Sport.R
import com.e.Sport.ui.recyclerviews.CellDesign.ListDialogDeisgn
import com.e.Sport.utils.visibility
import kotlinx.android.synthetic.main.list_recycler_design.view.*
import java.util.*
import kotlin.collections.HashSet

class CheckBoxListAdapter(val list:LinkedHashMap<String, Boolean>,
                          val checkList:HashSet<String>,
                          val onClick:(String,Boolean)->Unit
                  ) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val inflater=LayoutInflater.from(parent.context)
    val view=inflater.inflate(R.layout.list_recycler_design,parent,false)
    return ViewHolder(view)
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
            val key=list.keys.elementAt(position)
            val value=list[key]
            holder.textView.text=key
            holder.textView.setTypeface(Typeface.DEFAULT,ListDialogDeisgn.designMap[value]!!.second)
            holder.textView.setTextColor(ListDialogDeisgn.designMap[value]!!.first)
            holder.checkBox.visibility(!value!!)
            holder.checkBox.isChecked=checkList.contains(key)
        }
    }

    private inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textView=itemView.tviewListDialog
        val checkBox=itemView.checkBoxListDialog

        init {
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            onClick(list.keys.elementAt(adapterPosition),isChecked)
            }
        }
    }
}