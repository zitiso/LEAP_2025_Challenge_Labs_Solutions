package com.example.starwars.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.Person
import com.example.starwars.services.PeopleService
import kotlinx.coroutines.launch

class PeopleViewModel : ViewModel() {

    private val _people: MutableState<List<Person>> = mutableStateOf(emptyList())
    val people get() = _people

    fun getPeople() {
        viewModelScope.launch {
            val service = PeopleService.getInstance()
            try {
                val response = service.getPeople()
                _people.value = response
                Log.d("PeopleViewModel", "Loaded ${response.size} people")
            } catch (e: Exception) {
                Log.e("PeopleViewModel", "Failed to load people: ${e.message}")
            }
        }
    }
}
