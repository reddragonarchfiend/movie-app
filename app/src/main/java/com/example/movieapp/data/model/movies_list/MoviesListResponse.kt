package com.example.movieapp.data.model.movies_list

import com.example.movieapp.data.model.movie_details.MovieDetails

data class MoviesListResponse(
    val results : List<MovieDetails>,
    val total_results : Int,
    val total_pages : Int
)