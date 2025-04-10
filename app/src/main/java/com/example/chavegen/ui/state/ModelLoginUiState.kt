package com.example.chavegen.ui.state

data class ModelLoginUiState (
    val documentId: String = "",
    val siteName: String? = null,
    val username: String? = null,
    val password: String? = null,
    val siteUrl: String? = null
)