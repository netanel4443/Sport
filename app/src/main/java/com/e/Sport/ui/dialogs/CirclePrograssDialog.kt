package com.e.Sport.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import com.e.Sport.R
import java.lang.Exception

class CirclePrograssDialog(val context: Context) {
    private val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
    private val inf: LayoutInflater = LayoutInflater.from(context)
    private val view: View
    private val progressbar: ProgressBar
    private val alert: AlertDialog

    init {
        view = inf.inflate(R.layout.loading_progressbar, null)
        progressbar = view.findViewById(R.id.loadingProgressBar) as ProgressBar
        alertDialog.setView(view)
        alert = alertDialog.create()
        alert.setCancelable(false)
        alert.setCanceledOnTouchOutside(false)
        try { alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) }
        catch (e: Exception) { }
    }

    fun showprogressbar() {
        alert.show()
    }

    fun dismiss() {
        alert.dismiss()
    }
}