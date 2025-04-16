package com.example.employeeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

class MainViewModel : ViewModel() {

    var userList = mutableStateListOf<User>()
        private set

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    init {
        fetchUsers()
    }


    fun refreshUsers() {
        fetchUsers()
    }
    private fun fetchUsers() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitInstance.api.getUsers()
                userList.clear()
                userList.addAll(response.data)
                errorMessage = ""
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
