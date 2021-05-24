package com.sanjay.moviebuff.api

import com.sanjay.moviebuff.model.MovieApiResponse
import com.sanjay.moviebuff.utilities.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey:String = API_KEY
    ): MovieApiResponse
}