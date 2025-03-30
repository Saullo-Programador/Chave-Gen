package com.example.chavegen.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.chavegen.generated.generatedPassword
import com.example.chavegen.ui.state.GeneratedPasswordUiState
import com.example.chavegen.ui.state.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

): ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiStateGeneratedPassword = MutableStateFlow(GeneratedPasswordUiState())
    val uiStateGeneratedPassword = _uiStateGeneratedPassword.asStateFlow()

    private val _generatedPassword = MutableStateFlow("")
    val generatedPassword = _generatedPassword.asStateFlow()

    //Cadastro de login
    fun onNameSiteChange(newValue: String) {
        _uiState.value = _uiState.value.copy(nameSite = newValue)
    }

    fun onUrlSiteChange(newValue: String) {
        _uiState.value = _uiState.value.copy(urlSite = newValue)
    }

    fun onLoginSiteChange(newValue: String) {
        _uiState.value = _uiState.value.copy(loginSite = newValue)
    }

    fun onPasswordSiteChange(newValue: String) {
        _uiState.value = _uiState.value.copy(passwordSite = newValue)
    }


    //Gerar senha
    fun onPasswordOptionsChange(newState: GeneratedPasswordUiState) {
        _uiStateGeneratedPassword.value = newState
    }

    fun generatePassword() {
        val uiState = _uiStateGeneratedPassword.value
        _generatedPassword.value = generatedPassword(uiState)
    }


}