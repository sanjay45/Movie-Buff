package com.sanjay.moviebuff.di

import android.content.Context
import androidx.room.Room
import com.sanjay.moviebuff.dao.MovieDao
import com.sanjay.moviebuff.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java,"movie_database")
            .build()

    @Provides
    fun providesMovieDao(movieDatabase: MovieDatabase): MovieDao =
        movieDatabase.getDao()

}