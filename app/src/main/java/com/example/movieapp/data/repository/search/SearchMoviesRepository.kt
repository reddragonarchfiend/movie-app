package com.example.movieapp.data.repository.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movieapp.networking.NetworkService
import com.example.movieapp.ui.search.SearchMoviesPagingSource
import javax.inject.Inject

class SearchMoviesRepository @Inject constructor(
    private val service: NetworkService
) {
    fun searchMoviesList(query : String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchMoviesPagingSource(service,query) }
        ).flow
}