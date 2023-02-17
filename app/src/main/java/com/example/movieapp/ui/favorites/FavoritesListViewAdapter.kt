package com.example.movieapp.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.BR
import com.example.movieapp.data.model.movie_details.MovieDetails
import com.example.movieapp.databinding.ItemMovieBinding

class FavoritesListViewAdapter : ListAdapter<MovieDetails, FavoritesListViewAdapter.ViewHolder>(DIFF_UTIL) {
    var onClick : ((MovieDetails)-> Unit)? = null
    var onNotesClick : ((Int,String?)->Unit)? = null

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MovieDetails>() {
            override fun areItemsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun onMovieClick(listener:(MovieDetails)->Unit){
        onClick = listener
    }

    fun onNotesClick(notesListener:(Int,String?)->Unit){
        onNotesClick = notesListener
    }

    inner class ViewHolder(val viewDataBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        //bind movie details data to item
        holder.viewDataBinding.setVariable(BR.movieDetails, data)
        //show notes icon in favorites
        holder.viewDataBinding.setVariable(BR.hideNoteIcon,false)
        holder.viewDataBinding.root.setOnClickListener {
            onClick?.let {
                it(data!!)
            }
        }
        holder.viewDataBinding.ivNote.setOnClickListener {
            onNotesClick?.let {
                it(data.id!!,data.note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

}