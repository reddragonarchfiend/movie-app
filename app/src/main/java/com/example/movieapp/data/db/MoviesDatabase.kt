package com.example.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.model.movie_details.MovieDetails

@Database(entities = [MovieDetails::class], version = 1, exportSchema = true)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}