package com.example.starwars.people.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.people.data.Person
import com.example.starwars.people.services.FilmService
import com.example.starwars.people.services.PeopleService
import com.example.starwars.people.services.PlanetService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val _person : MutableLiveData<Person> = MutableLiveData(Person.empty())
    val person get() = _person

    fun getPerson(id: Int) {
        viewModelScope.launch {
            val peopleService = PeopleService.getInstance()
            val planetService = PlanetService.getInstance()
            val filmService = FilmService.getInstance()

            try {
                // Load the person object
                val tempPerson = peopleService.getPerson(id)

                // --- Homeworld (follow URL) ---
                tempPerson.homeworld.let { hwUrl ->
                    // Some payloads include trailing slash; Retrofit handles both
                    val planet = planetService.getPlanetByUrl(hwUrl)
                    tempPerson.homeworld = planet.name
                }

                // --- Films (follow URLs) ---
                val films = tempPerson.films?.map { filmUrl ->
                    async { runCatching { filmService.getFilmByUrl(filmUrl) }.getOrNull() }
                }?.awaitAll()?.filterNotNull().orEmpty()

                // Sort and format: "Episode IV: A New Hope"
                val formatted = films
                    .sortedBy { it.episode_id }
                    .map { film -> "Episode ${toRomanNumeral(film.episode_id)}: ${film.title}" }

                tempPerson.films = formatted

                _person.value = tempPerson
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// unchanged helper
fun toRomanNumeral(number: Int): String {
    val romanNumerals = listOf("0","I","II","III","IV","V","VI","VII","VIII","IX")
    return if (number in 0..9) romanNumerals[number] else number.toString()
}
