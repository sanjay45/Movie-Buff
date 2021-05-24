package com.sanjay.moviebuff.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sanjay.moviebuff.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table ORDER BY releaseDate DESC")
    fun getMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM movie_table WHERE `id` = :id")
    fun getMovie(id: Int): LiveData<Movie>
}