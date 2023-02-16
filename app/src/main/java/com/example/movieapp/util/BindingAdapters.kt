package com.example.movieapp.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.movie_details.MovieGenres
import com.example.movieapp.data.model.movie_details.ProductionCompanies
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("loadImage","imageSize")
fun ImageView.loadImage(url : String?, imageSize : String){
    url?.let {
        Glide.with(this).load(Const.BASE_URL_IMAGES+imageSize+it).into(this)
    }
}

@BindingAdapter("setBudget")
fun TextView.setBudget(budget : Int?){
    text = if(budget != 0 && budget != null){
        this.context.getString(R.string.budget_dollar, budget.toString())
    }
    else{
        context.getString(R.string.not_available)
    }
}

@BindingAdapter("setRuntime")
fun TextView.setRuntime(runtime : Int?){
    text = runtime?.let {
        this.context.getString(R.string.minutes, it.toString())
    }?: run {
         context.getString(R.string.not_available)
    }
}

@BindingAdapter("setReleaseDate")
fun TextView.setReleaseDate(releaseDate : String?){
    releaseDate?.let {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(it)!!
        text = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(date)
    }
}

@BindingAdapter("setGenresListAsText")
fun TextView.setGenresListAsText(genresList : List<MovieGenres>?){
    var genresString = ""
    genresList?.let {
        for(i in it.indices){
            val item = it[i]
            genresString += item.name

            if(i != it.size-1){
                genresString += "\n"
            }
        }
    }?: run {
        genresString = context.getString(R.string.not_available)
    }
    text = genresString

}

@BindingAdapter("setPCListAsText")
fun TextView.setProductionCompaniesListAsText(pcList : List<ProductionCompanies>?){
    var pcString = ""
    pcList?.let {
        for(i in it.indices){
            val item = it[i]
            pcString += item.name

            if(i != it.size-1){
                pcString += "\n"
            }
        }
    }?: run {
        pcString = context.getString(R.string.not_available)
    }
    text = pcString
}

@BindingAdapter("setText")
fun TextView.setText(textString : String?){
    text = if(!textString.isNullOrEmpty()){
        textString
    }
    else{
        context.getString(R.string.not_available)
    }
}