package com.example.movieapp.data.repository.movies_list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movieapp.ui.movies_list.MoviesPagingSource
import com.example.movieapp.networking.NetworkService
import javax.inject.Inject

class MoviesListRepository @Inject constructor(
    private val service: NetworkService
) {
    fun getMoviesList() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(service) }
        ).flow
}