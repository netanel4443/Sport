package com.e.Sport.ui.dialogs

import android.app.AlertDialog
import android.content.Context


class SimpleTextDialog  {
    fun show(context: Context,message:String,onClick:()->Unit) {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setPositiveButton("yes") { dialog, which ->
                onClick()
            }
            alertDialog.setNegativeButton("no") { dialog, which ->
                dialog.dismiss()
            }
            alertDialog.setOnDismissListener{ }
            alertDialog.setMessage(message)
            alertDialog.show()
        }
}