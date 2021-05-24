package com.sanjay.moviebuff.repository

import com.sanjay.moviebuff.api.MovieApiService
import com.sanjay.moviebuff.dao.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApiService: MovieApiService,
               private val movieDao: MovieDao){

     fun getMovieList() = movieDao.getMovies()

    fun getMovie(id:Int) = movieDao.getMovie(id)

    suspend fun fetchFromNetwork() {
        withContext(Dispatchers.IO) {
            val response = movieApiService.getMovies()
            movieDao.insertMovies(response.results)
        }
    }

    suspend fun deleteAllMovies() = movieDao.deleteAllData()


}