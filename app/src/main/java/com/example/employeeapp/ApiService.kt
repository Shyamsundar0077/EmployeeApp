package com.example.employeeapp

import retrofit2.http.GET

interface ApiService {
    @GET("api/users?page=2")
    suspend fun getUsers(): UserResponse
}
