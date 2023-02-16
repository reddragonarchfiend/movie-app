package com.example.movieapp.data.repository.favorites

import com.example.movieapp.data.db.MoviesDao
import com.example.movieapp.data.model.movie_details.MovieDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
   private val moviesDao: MoviesDao
) {
    fun getFavoriteMovies(): Flow<List<MovieDetails>> = moviesDao.getFavouriteMovies()
    fun updateNoteForMovie(movieId:Int,note:String?) = moviesDao.updateNoteForMovie(movieId,note)
}