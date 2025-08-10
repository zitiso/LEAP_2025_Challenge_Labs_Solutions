package com.example.starwars.people.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {

    private val _sharedId = MutableStateFlow(1)
    val sharedId: StateFlow<Int> = _sharedId

    fun updateInt(newId: Int) {
        _sharedId.value = newId
    }

}