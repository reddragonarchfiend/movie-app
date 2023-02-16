package com.example.movieapp.data.model.movie_details

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

//TODO da viime so e rabotta
@Entity(tableName = "movies")
data class MovieDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val budget: Int? = null,
    val homepage: String? = null,
    val imdb_id: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val revenue: Int? = null,
    val runtime: Int? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
    val first_air_date: String? = null,
    val note : String? = null

) : Serializable{
    @Ignore val production_companies: List<ProductionCompanies>? = null
    @Ignore val production_countries: List<ProductionCountries>? = null
    @Ignore val spoken_languages: List<SpokenLanguages>? = null
    @Ignore val genres: List<MovieGenres>? = null
}









