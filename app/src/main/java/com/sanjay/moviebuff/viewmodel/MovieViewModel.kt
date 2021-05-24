package com.sanjay.moviebuff.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjay.moviebuff.model.Movie
import com.sanjay.moviebuff.repository.MovieRepository
import com.sanjay.moviebuff.utilities.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository):
    ViewModel() {

    private val _status = MutableLiveData<NetworkStatus>()
    val status: LiveData<NetworkStatus> = _status

    val moviesList: LiveData<List<Movie>> = movieRepository.getMovieList()

    fun getMovie(id:Int): LiveData<Movie> = movieRepository.getMovie(id)

    fun fetchFromNetwork() = viewModelScope.launch {
        _status.value = NetworkStatus.Loading
        try {
             movieRepository.fetchFromNetwork()
            _status.value = NetworkStatus.Success
        } catch (networkError : Exception) {
            _status.value = NetworkStatus.Error
        }
    }

    fun deleteAllMovies() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            movieRepository.deleteAllMovies()
        }
    }


}