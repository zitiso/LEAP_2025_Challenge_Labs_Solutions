package com.example.starwars.people.services

import com.example.starwars.people.data.Person
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PeopleService {
    @GET("people")
    suspend fun getPeople(): List<Person>   // ← array, not wrapper

    @GET("people/{id}")
    suspend fun getPerson(@Path("id") id: Int): Person

    // PeopleService (optional convenience)
    @GET
    suspend fun getPersonByUrl(@Url personUrl: String): Person

    companion object {
        @Volatile private var instance: PeopleService? = null
        fun getInstance(): PeopleService =
            instance ?: synchronized(this) {
                instance ?: Retrofit.Builder()
                    .baseUrl("https://swapi.info/api/")   // ← correct base
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PeopleService::class.java)
                    .also { instance = it }
            }
    }
}
