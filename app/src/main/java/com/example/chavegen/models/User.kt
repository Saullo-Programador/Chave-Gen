package com.example.chavegen.models

data class User (
    val userId: String = "",
    val fullName: String? = null,
    val email: String? = "",
    val password: String? = ""
)