package com.example.movieapp.data.repository.popular

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movieapp.ui.popular.PopularListPagingSource
import com.example.movieapp.networking.NetworkService
import javax.inject.Inject

class PopularListRepository @Inject constructor(
    private val service: NetworkService
) {
    fun getMoviesList() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PopularListPagingSource(service) }
        ).flow
}