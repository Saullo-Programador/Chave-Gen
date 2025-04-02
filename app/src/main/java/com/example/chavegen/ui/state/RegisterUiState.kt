package com.example.chavegen.ui.state

data class RegisterUiState (
    val siteName: String = "",
    val siteUrl: String = "",
    val siteUser: String = "",
    val sitePassword: String = "",
    val onSiteNameChange: (String) -> Unit = {},
    val onSiteUrlChange: (String) -> Unit = {},
    val onSiteUserChange: (String) -> Unit = {},
    val onSitePasswordChange: (String) -> Unit = {},
)