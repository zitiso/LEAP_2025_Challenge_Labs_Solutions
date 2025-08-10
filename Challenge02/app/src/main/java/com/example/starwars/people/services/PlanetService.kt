package com.example.starwars.people.services

import com.example.starwars.people.StarWarsConstants
import com.example.starwars.people.data.Planet
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PlanetService {

    @GET("planets")
    suspend fun getPlanets(): List<Planet>

    @GET("planets/{id}")
    suspend fun getPlanet(@Path("id") id: Int): Planet

    // Fetch using the full URL from Person.homeworld
    @GET
    suspend fun getPlanetByUrl(@Url planetUrl: String): Planet

    companion object {
        var planetService :PlanetService ?= null
        fun getInstance() :PlanetService {
            if( planetService == null ) {
                planetService = Retrofit.Builder()
                    .baseUrl(StarWarsConstants.SWAPI_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PlanetService::class.java)
            }
            return planetService!!
        }
    }
}