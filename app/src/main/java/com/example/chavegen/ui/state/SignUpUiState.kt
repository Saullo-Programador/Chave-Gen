package com.example.chavegen.ui.state

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