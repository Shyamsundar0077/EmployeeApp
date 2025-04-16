package com.example.employeeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = repository.getUsers()
                _users.value = response.data
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun getFilteredUsers(): List<User> {
        val query = _searchQuery.value.lowercase()
        return _users.value.filter {
            it.firstName.lowercase().contains(query)
        }
    }
}
