package com.e.Sport.ui.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.Sport.R

class GroceryListsAdapter(
    private var listsNames:HashSet<String>,
    val onClick:(String, ClickTypes)->Unit)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ClickTypes{ click,longClick }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_grocerylists, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
            holder.listName.text=listsNames.elementAt(position)
            println("sdf ${listsNames.elementAt(position)}")
        }
    }

    override fun getItemCount(): Int {
        return listsNames.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var listName = itemView.findViewById(R.id.listNameTviewRecyclerGroceryLists) as TextView
        var deleteBtn=itemView.findViewById(R.id.deleteBtnrRcyclerGroceryLists) as Button

        init {
            listName.setOnClickListener { onClick(listName.text.toString(),ClickTypes.click) }
            deleteBtn.setOnClickListener { onClick(listName.text.toString(),ClickTypes.longClick) }
        }
    }
}






