package com.example.movieapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.BR
import com.example.movieapp.data.model.movie_details.MovieDetails
import com.example.movieapp.databinding.ItemMovieBinding

class ListPagingAdapter : PagingDataAdapter<MovieDetails, ListPagingAdapter.ViewHolder>(
    DIFF_UTIL
) {

    var onClick : ((MovieDetails)-> Unit)? = null

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

    inner class ViewHolder(val viewDataBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.viewDataBinding.setVariable(BR.movieDetails, data)
        //hide or show icon depending on adapter
        holder.viewDataBinding.setVariable(BR.hideNoteIcon,true)
        holder.viewDataBinding.root.setOnClickListener {
            onClick?.let {
                it(data!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}