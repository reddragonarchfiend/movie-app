package com.example.movieapp.data.db

import androidx.room.*
import com.example.movieapp.data.model.movie_details.MovieDetails
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getFavouriteMovies(): Flow<List<MovieDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieDetails: MovieDetails)

    @Delete
    suspend fun delete(movieDetails: MovieDetails)

    @Query("select COUNT(*) from movies where id = :movieId")
    fun isMovieAlreadySaved(movieId:Int) : Int
}