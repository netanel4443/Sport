package com.e.Sport.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.e.Sport.R
import com.e.Sport.data.Exercise
import com.e.Sport.data.GroceryItem
import kotlinx.android.synthetic.main.grocery_item_add_or_edit_dialog.view.*

class AddOrChangeExerciseDialog {

    private lateinit var reps:EditText
    private lateinit var sets:EditText
    private lateinit var exerciseName:EditText
    private lateinit var repsTitle:TextView
    private lateinit var setsTitle:TextView

    fun showDialog(context: Context, exercise: Exercise, newItem:(Exercise)->Unit){
        val alertDialog=AlertDialog.Builder(context)
        val inflater=LayoutInflater.from(context)
        val view=inflater.inflate(R.layout.grocery_item_add_or_edit_dialog,null)
        val addOrEdit=view.addOrEditAddItemDialog
        reps=view.minPriceAddItemDialog
        sets=view.maxPriceAddItemDialog
        exerciseName=view.itemNameAddItemDialog
        repsTitle=view.secondTextViewAddItemDialog
        setsTitle=view.thirdTextViewAddItemDialog

        initItem(exercise)

        alertDialog.setView(view)

        val alert=alertDialog.create()

        addOrEdit.setOnClickListener {
            exercise.reps=reps.text.toString().toInt()
            exercise.sets=sets.text.toString().toInt()
            exercise.name=exerciseName.text.toString()
            newItem(exercise)
            alert.dismiss()
        }
        alert.show()
    }

    private fun initItem(exercise: Exercise) {
       repsTitle.text="reps:"
       setsTitle.text="sets:"
       reps.setText(exercise.reps.toString())
       sets.setText(exercise.sets.toString())
       exerciseName.setText(exercise.name )
    }
}