package com.example.starwars.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.Planet
import com.example.starwars.services.PlanetService
import kotlinx.coroutines.launch

class PlanetViewModel : ViewModel() {

    private val _planets: MutableState<List<Planet>> = mutableStateOf(emptyList())
    val planets get() = _planets

    fun getPlanets() {
        viewModelScope.launch {
            val planetService = PlanetService.getInstance()
            try {
                _planets.value = planetService.getPlanets()
            } catch (e: Exception) {
                Log.e("PlanetViewModel", "Failed to load planets: ${e.message}")
            }
        }
    }
}
