package com.e.Sport.ui.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.e.Sport.R
import com.e.Sport.data.Exercise
import com.e.Sport.data.GroceryItem

class ExercisesRecyclerAdapter(
    var groceryList:HashMap<String, Exercise>,
    val click:(Exercise)->Unit)
            :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflter=LayoutInflater.from(parent.context)
        val view=inflter.inflate(R.layout.grocery_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groceryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
        val item=groceryList.entries.elementAt(position).value

        holder.minPrice.text= item.reps.toString()
        holder.maxPrice.text=item.sets.toString()
        holder.itemName.text=item.name
        }
    }

    inner class ViewHolder( itemView: View):RecyclerView.ViewHolder(itemView){
        val mainCardView=itemView.findViewById(R.id.mainCardViewGroceryItem) as CardView
        val minPrice=itemView.findViewById(R.id.groceryItemMinPrice) as TextView
        val maxPrice=itemView.findViewById(R.id.groceryItemMaxPrice) as TextView
        val itemName=itemView.findViewById(R.id.groceryItem) as TextView
        val editButton=itemView.findViewById(R.id.editItemBtnGroceryItem) as Button

        init {
            editButton.setOnClickListener{
                click(groceryList.entries.elementAt(adapterPosition).value)}
        }
    }

}
