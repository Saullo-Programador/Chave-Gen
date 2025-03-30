package com.example.chavegen.models

data class User (
    val userId: String = "",
    val fullName: String? = null,
    val email: String? = ""
)

data class SignUpUiState(
    val user: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val onUserChange: (String) -> Unit = {},
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onConfirmPasswordChange: (String) -> Unit = {},
    val error: String? = null
)

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInErrorMessage: String? = null
)