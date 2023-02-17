package com.example.movieapp.data.model.popular

import com.example.movieapp.data.model.movie_details.MovieDetails

data class ListResponse(
    val results : List<MovieDetails>,
    val total_results : Int,
    val total_pages : Int
)