package com.sanjay.moviebuff.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sanjay.moviebuff.dao.MovieDao
import com.sanjay.moviebuff.model.Movie


@Database(entities = [Movie::class],version = 1,exportSchema = false)
abstract class MovieDatabase : RoomDatabase(){

    abstract fun getDao() : MovieDao

}