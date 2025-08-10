package com.example.starwars.people.services


import com.example.starwars.people.data.Film
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface FilmService {
    @GET("films")
    suspend fun getFilms(): List<Film>   // ← array, not wrapper

    @GET("film/{id}")
    suspend fun getFilm(@Path("id") id: Int): Film

    // Fetch using the full URL from Person.films[]
    @GET
    suspend fun getFilmByUrl(@Url filmUrl: String): Film

    companion object {
        @Volatile private var instance: FilmService? = null
        fun getInstance(): FilmService =
            instance ?: synchronized(this) {
                instance ?: Retrofit.Builder()
                    .baseUrl("https://swapi.info/api/")   // ← correct base
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(FilmService::class.java)
                    .also { instance = it }
            }
    }
}
