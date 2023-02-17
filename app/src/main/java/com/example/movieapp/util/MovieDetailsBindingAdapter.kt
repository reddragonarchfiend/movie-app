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

@BindingAdapter("loadImage", "imageSize")
fun ImageView.loadImage(url: String?, imageSize: String) {
        Glide.with(this).load(Const.BASE_URL_IMAGES + imageSize + url)
            .error(R.drawable.ic_close_24).into(this)
}

@BindingAdapter("setBudget")
fun TextView.setBudget(budget: Int?) {
    text = if (budget != 0 && budget != null) {
        this.context.getString(R.string.budget_dollar, budget.toString())
    } else {
        context.getString(R.string.not_available)
    }
}

@BindingAdapter("setRuntime")
fun TextView.setRuntime(runtime: Int?) {
    text = if (runtime != 0 && runtime != null) {
        this.context.getString(R.string.minutes, runtime.toString())
    }
    else{
        context.getString(R.string.not_available)
    }
}

@BindingAdapter("setReleaseDate")
fun TextView.setReleaseDate(releaseDate: String?) {
    text = if (!releaseDate.isNullOrEmpty()) {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(releaseDate)!!
        SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(date)
    } else {
        context.getString(R.string.not_available)
    }
}

@BindingAdapter("setGenresListAsText")
fun TextView.setGenresListAsText(genresList: List<MovieGenres>?) {
    var genresString = ""
    if (!genresList.isNullOrEmpty()) {
        genresList.let {
            //add a new line of text for each item
            for (i in it.indices) {
                val item = it[i]
                genresString += item.name

                if (i != it.size - 1) {
                    genresString += "\n"
                }
            }
        }
    } else {
        genresString = context.getString(R.string.not_available)
    }

    text = genresString

}

@BindingAdapter("setPCListAsText")
fun TextView.setProductionCompaniesListAsText(pcList: List<ProductionCompanies>?) {
    var pcString = ""
    if (!pcList.isNullOrEmpty()) {
        pcList.let {
            //add a new line of text for each item
            for (i in it.indices) {
                val item = it[i]
                pcString += item.name

                if (i != it.size - 1) {
                    pcString += "\n"
                }
            }
        }
    } else {
        pcString = context.getString(R.string.not_available)
    }

    text = pcString
}

@BindingAdapter("setText")
fun TextView.setText(textString: String?) {
    text = if (!textString.isNullOrEmpty()) {
        textString
    } else {
        context.getString(R.string.not_available)
    }
}