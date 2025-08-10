package com.example.starwars.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.Movie
import com.example.starwars.services.MovieService
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val _movies: MutableState<List<Movie>> = mutableStateOf(emptyList())
    val movies get() = _movies

    fun getMovies() {
        viewModelScope.launch {
            val movieService = MovieService.getInstance()
            try {
                _movies.value = movieService.getMovies()
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Failed to load movies: ${e.message}")
            }
        }
    }
}