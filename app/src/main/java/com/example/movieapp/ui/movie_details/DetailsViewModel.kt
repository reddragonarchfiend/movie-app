package com.example.movieapp.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.movie_details.MovieDetails
import com.example.movieapp.data.repository.movie_details.MovieDetailsRepository
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: MovieDetailsRepository): ViewModel() {

    private val _movieDetails = MutableLiveData<Resource<MovieDetails>>()
    val movieDetails: LiveData<Resource<MovieDetails>>
        get() = _movieDetails

    private var _isMovieSaved = MutableLiveData<Boolean>()
    val isMovieSaved: LiveData<Boolean>
        get() = _isMovieSaved

    fun saveFavoriteMovie(movieDetails: MovieDetails) = viewModelScope.launch {
        repository.addToFavorite(movieDetails = movieDetails)
    }

    fun deleteFavoriteMovie(movieDetails: MovieDetails) = viewModelScope.launch {
        repository.deleteFromFavorite(movieDetails = movieDetails)
    }

    fun checkIfMovieSaved(movieDetails : MovieDetails) = viewModelScope.launch(Dispatchers.IO) {
        if (repository.isMovieAlreadySaved(movieDetails) >= 1) {
            _isMovieSaved.postValue(true)
        } else {
            _isMovieSaved.postValue(false)
        }
    }

    fun getMovieDetails(movieId : Int){
        viewModelScope.launch {
            _movieDetails.postValue(Resource.loading())

            val response = try {
                repository.getMovieDetails(movieId).let {
                    if (it.isSuccessful) {
                        Resource.success(it.body()!!)
                    } else {
                        Resource.error()
                    }
                }
            } catch (e: Exception) {
                Resource.error()
            }
            _movieDetails.postValue(response)
        }
    }
}