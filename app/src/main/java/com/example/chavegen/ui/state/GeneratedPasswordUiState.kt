package com.example.chavegen.ui.state

data class GeneratedPasswordUiState(
    val length: Int = 12,
    val useUppercase: Boolean = true,
    val useLowercase: Boolean = true,
    val useNumbers: Boolean = true,
    val useSymbols: Boolean = false,
)