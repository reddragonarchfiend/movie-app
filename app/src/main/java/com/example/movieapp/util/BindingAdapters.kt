package com.example.movieapp.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movieapp.data.model.movie_details.MovieGenres
import com.example.movieapp.data.model.movie_details.ProductionCompanies

@BindingAdapter("loadSmallerImage")
fun loadSmallerImage(view:ImageView,url : String?){
    url?.let {
        Glide.with(view).load(Const.BASE_URL_IMAGES+Const.SMALLER_IMAGE+it).into(view)
    }
}

@BindingAdapter("loadLargerImage")
fun loadLargerImage(view:ImageView,url : String?){
    url?.let {
        Glide.with(view).load(Const.BASE_URL_IMAGES+Const.LARGER_IMAGE+it).into(view)
    }

}

@BindingAdapter("setIntAsText")
fun setIntAsText(view: TextView,int : Int?){
    int?.let {
        view.text = int.toString()
    }

}

@BindingAdapter("setGenresListAsText")
fun setGenresListAsText(view: TextView,genresList : List<MovieGenres>?){
    var genresString = ""
    genresList?.let {
        it.forEach {
            genresString = genresString + it.name+","
        }
    }
    view.text = genresString

}

@BindingAdapter("setPCListAsText")
fun setProductionCompaniesListAsText(view: TextView, pcList : List<ProductionCompanies>?){
    var pcString = ""
    pcList?.let {

        it.forEach {
            pcString = pcString + it.name+","
        }
    }
    view.text = pcString

}