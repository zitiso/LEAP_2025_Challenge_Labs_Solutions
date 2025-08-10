package com.example.starwars.people.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.people.data.Person
import com.example.starwars.people.services.PeopleService
import kotlinx.coroutines.launch

class MasterViewModel(
    private val peopleService: PeopleService = PeopleService.getInstance()
) : ViewModel() {

    private val _people = MutableLiveData<List<Person>>(emptyList())
    val people: LiveData<List<Person>> get() = _people

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?> get() = _error

    fun getPeople() {
        if (_loading.value == true) return
        _loading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                val list = peopleService.getPeople()        // ← single call, returns array
                _people.value = list.sortedBy { it.name }
            } catch (t: Throwable) {
                _error.value = t
            } finally {
                _loading.value = false
            }
        }
    }
}
