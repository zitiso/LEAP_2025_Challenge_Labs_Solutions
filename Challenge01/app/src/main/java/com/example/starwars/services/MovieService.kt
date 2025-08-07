package com.example.starwars.services

import com.example.starwars.StarWarsConstants.BASE_URL
import com.example.starwars.data.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MovieService {

    @GET("films")
    suspend fun getMovies(): List<Movie>

    companion object {
        private var movieService: MovieService? = null

        fun getInstance(): MovieService {
            if (movieService == null) {
                movieService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MovieService::class.java)
            }
            return movieService!!
        }
    }
}
