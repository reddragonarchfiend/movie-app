package com.example.movieapp.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.data.model.movie_details.MovieDetails
import com.example.movieapp.data.repository.popular.PopularListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PopularListViewModel @Inject constructor(private val movieRepository: PopularListRepository): ViewModel() {
    //get the flow of paging data
    fun getMovieList():Flow<PagingData<MovieDetails>>{
        return movieRepository.getMoviesList().cachedIn(viewModelScope)
    }
}