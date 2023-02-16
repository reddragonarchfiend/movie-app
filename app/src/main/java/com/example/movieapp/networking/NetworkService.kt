package com.example.movieapp.networking

import com.example.movieapp.data.model.movie_details.MovieDetails
import com.example.movieapp.data.model.movies_list.MoviesListResponse
import retrofit2.Response
import retrofit2.http.*

const val MOVIE = "movie"

interface NetworkService {

    //Movies list
    @GET("$MOVIE/popular")
    suspend fun getMoviesList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): MoviesListResponse

    //Movie details
    @GET("$MOVIE/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
    ): Response<MovieDetails>
}