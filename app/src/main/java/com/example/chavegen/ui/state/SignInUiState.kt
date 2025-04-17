package com.example.chavegen.ui.state

data class SignInUiState (
    val email: String = "",
    val password: String = "",
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val isShowPassword: Boolean = false,
    val isAuthenticated: Boolean = false,
    val erro: String? = null,
    val isResetEmailSent: Boolean = false
)