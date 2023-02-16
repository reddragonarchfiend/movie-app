package com.example.movieapp.data.repository.movie_details

import com.example.movieapp.data.db.MoviesDao
import com.example.movieapp.data.model.movie_details.MovieDetails
import com.example.movieapp.networking.NetworkService
import com.example.movieapp.util.Const
import javax.inject.Inject

class MovieDetailsRepository@Inject constructor(
    private val service: NetworkService,
    private val moviesDao: MoviesDao
)  {
    suspend fun getMovieDetails(movieId : Int) = service.getMovieDetails(movieId,Const.API_KEY)

    suspend fun addToFavorite(movieDetails: MovieDetails) = moviesDao.insert(movieDetails = movieDetails)
    suspend fun deleteFromFavorite(movieDetails: MovieDetails) = moviesDao.delete(movieDetails = movieDetails)
    suspend fun isMovieAlreadySaved(movieDetails: MovieDetails) = moviesDao.isMovieAlreadySaved(movieDetails.id!!)
}