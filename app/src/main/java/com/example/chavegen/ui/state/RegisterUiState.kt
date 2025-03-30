package com.example.chavegen.ui.state

data class RegisterUiState (
    val nameSite: String = "",
    val urlSite: String = "",
    val loginSite: String = "",
    val passwordSite: String = "",
    val onNameSiteChange: (String) -> Unit = {},
    val onUrlSiteChange: (String) -> Unit = {},
    val onLoginSiteChange: (String) -> Unit = {},
    val onPasswordSiteChange: (String) -> Unit = {},
)