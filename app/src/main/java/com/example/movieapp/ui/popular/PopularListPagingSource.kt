package com.example.movieapp.ui.popular

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.movie_details.MovieDetails
import com.example.movieapp.networking.NetworkService
import com.example.movieapp.util.Const
import com.example.movieapp.util.Const.STARTING_INDEX

class PopularListPagingSource(
    private val networkService: NetworkService
) : PagingSource<Int, MovieDetails>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDetails> {
        val position = params.key ?: STARTING_INDEX

        return try{
            val response = networkService.getMoviesList(Const.API_KEY,position)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, MovieDetails>): Int? {
        // get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }
}