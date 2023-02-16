package com.example.movieapp.util

import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("hideNotesIcon")
fun ImageView.setNotesIconVisibility(hideNotesIcon : Boolean){
    this.isVisible = !hideNotesIcon
}