package com.example.starwars.people.data


data class Person(
    val name: String,
    val height: String,
    val mass: String,
    val hair_color: String,
    val skin_color: String,
    val eye_color: String,
    val birth_year: String,
    val gender: String,
    var homeworld: String,
    var films: List<String>?,
    val species: List<String>?,
    val vehicles: List<String>?,
    val starships: List<String>?,
    val created: String,
    val edited: String,
    val url: String
) {
    companion object {
        fun empty() = Person(
            name = "",
            height = "",
            mass = "",
            hair_color = "",
            skin_color = "",
            eye_color = "",
            birth_year = "",
            gender = "",
            homeworld = "",
            films = emptyList(),
            species = emptyList(),
            vehicles = emptyList(),
            starships = emptyList(),
            created = "",
            edited = "",
            url = ""
        )
    }
}
