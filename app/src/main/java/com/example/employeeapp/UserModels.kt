package com.example.employeeapp


import com.google.gson.annotations.SerializedName

data class UserResponse(
    val data: List<User>
)

data class User(
    val id: Int,
    val email: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    val avatar: String
)
