package com.example.movieapp.ui.movies_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.data.model.movie_details.MovieDetails
import com.example.movieapp.data.repository.movies_list.MoviesListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MoviesListRepository): ViewModel() {

    fun getMovieList():Flow<PagingData<MovieDetails>>{
        return movieRepository.getMoviesList().cachedIn(viewModelScope)
    }
}