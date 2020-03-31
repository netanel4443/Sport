package com.e.Sport.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.e.Sport.R
import com.e.Sport.data.GroceryItem
import kotlinx.android.synthetic.main.grocery_item_add_or_edit_dialog.view.*

class AddItemDialog {

    private lateinit var minPrice:EditText
    private lateinit var maxPrice:EditText
    private lateinit var itemName:EditText
    private lateinit var minPriceTitle: TextView
    private lateinit var maxPriceTitle: TextView

    fun showDialog(context: Context, groceryItem: GroceryItem, newItem:(GroceryItem)->Unit){
        val alertDialog=AlertDialog.Builder(context)
        val inflater=LayoutInflater.from(context)
        val view=inflater.inflate(R.layout.grocery_item_add_or_edit_dialog,null)
        val addOrEdit=view.addOrEditAddItemDialog
        minPrice=view.minPriceAddItemDialog
        maxPrice=view.maxPriceAddItemDialog
        itemName=view.itemNameAddItemDialog
        minPriceTitle=view.secondTextViewAddItemDialog
        maxPriceTitle=view.thirdTextViewAddItemDialog

        initItem(groceryItem)

        alertDialog.setView(view)

        val alert=alertDialog.create()

        addOrEdit.setOnClickListener {
            groceryItem.minPrice=minPrice.text.toString().toFloat()
            groceryItem.maxPrice=maxPrice.text.toString().toFloat()
            groceryItem.name=itemName.text.toString()
            newItem(groceryItem)
            alert.dismiss()
        }
        alert.show()
    }

    private fun initItem(groceryItem: GroceryItem) {
       minPriceTitle.text="Min price:"
       maxPriceTitle.text="Max price:"
       minPrice.setText(groceryItem.minPrice.toString())
       maxPrice.setText(groceryItem.maxPrice.toString())
       itemName.setText(groceryItem.name )
    }
}