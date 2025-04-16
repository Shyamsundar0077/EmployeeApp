package com.example.employeeapp

class UserRepository {
    suspend fun getUsers(): UserResponse {
        return RetrofitInstance.api.getUsers()
    }
}
