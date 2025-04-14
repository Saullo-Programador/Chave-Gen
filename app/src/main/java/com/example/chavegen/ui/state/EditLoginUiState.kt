package com.example.chavegen.ui.state

data class EditLoginUiState(
    val documentId: String = "",
    val siteName: String = "",
    val siteUrl: String = "",
    val siteUser: String = "",
    val sitePassword: String = "",

    val onSiteNameChange: (String) -> Unit = {},
    val onSiteUrlChange: (String) -> Unit = {},
    val onSiteUserChange: (String) -> Unit = {},
    val onSitePasswordChange: (String) -> Unit = {},

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)