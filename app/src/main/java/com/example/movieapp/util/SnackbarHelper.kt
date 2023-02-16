package com.example.movieapp.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackbarHelper {

    fun createShortSnackbar(view : View,text: String, anchorView : View? = null){
        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        snackbar.anchorView = anchorView
        snackbar.show()
    }
}