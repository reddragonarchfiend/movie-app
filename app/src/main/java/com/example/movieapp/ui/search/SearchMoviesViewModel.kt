package com.example.movieapp.ui.search

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.movieapp.data.repository.search.SearchMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    private val repository: SearchMoviesRepository
) : ViewModel() {

    val query = MutableStateFlow("")

    //transform flow
    val movies = query.flatMapLatest { query ->
        repository.searchMoviesList(query).cachedIn(viewModelScope)
    }

   fun doSearching(queryString : String){
       viewModelScope.launch {
           query.emit(queryString)
       }
   }
}