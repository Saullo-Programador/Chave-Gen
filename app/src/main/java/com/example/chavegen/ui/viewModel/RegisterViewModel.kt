package com.example.chavegen.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.chavegen.authentication.FirebaseAuthRepository
import com.example.chavegen.generated.generatedPassword
import com.example.chavegen.repository.FireStoreRepository
import com.example.chavegen.ui.state.GeneratedPasswordUiState
import com.example.chavegen.ui.state.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val fireStoreRepository: FireStoreRepository,
    private val fireAuth: FirebaseAuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiStateGeneratedPassword = MutableStateFlow(GeneratedPasswordUiState())
    val uiStateGeneratedPassword = _uiStateGeneratedPassword.asStateFlow()

    private val _generatedPassword = MutableStateFlow("")
    val generatedPassword = _generatedPassword.asStateFlow()

    private val _eventMessage = MutableStateFlow<String?>(null)
    val eventMessage = _eventMessage.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onSiteNameChange = { siteName ->
                    _uiState.update {
                        it.copy(siteName = siteName)
                    }
                },
                onSiteUrlChange = { siteUrl ->
                    _uiState.update {
                        it.copy(siteUrl = siteUrl)
                    }
                },
                onSiteUserChange = { siteUser ->
                    _uiState.update {
                        it.copy(siteUser = siteUser)
                    }
                },
                onSitePasswordChange = { sitePassword ->
                    _uiState.update {
                        it.copy(sitePassword = sitePassword)
                    }
                }
            )
        }
    }

    //Gerar senha
    fun onPasswordOptionsChange(newState: GeneratedPasswordUiState) {
        _uiStateGeneratedPassword.value = newState
    }

    fun generatePassword() {
        val uiState = _uiStateGeneratedPassword.value
        _generatedPassword.value = generatedPassword(uiState)
    }

    //salvar login
    fun verificarLogin(): Boolean {
        val userId = fireAuth.getCurrentUserId()
        return if (
            _uiState.value.siteName.isNotEmpty() &&
            _uiState.value.siteUrl.isNotEmpty() &&
            _uiState.value.siteUser.isNotEmpty() &&
            _uiState.value.sitePassword.isNotEmpty()
        ) {
            val itemLogin = com.example.chavegen.models.ItemLogin(
                siteName = _uiState.value.siteName,
                siteUrl = _uiState.value.siteUrl,
                siteUser = _uiState.value.siteUser,
                sitePassword = _uiState.value.sitePassword
            )
            fireStoreRepository.salvarLogin(
                userId = userId ?: return false,
                itemLogin = itemLogin
            )
            _eventMessage.value = "Sucesso ao salvar o login"
            true
        } else {
            _eventMessage.value = "Preencha os campos de Nome do Site, Login e Senha"
            false
        }
    }
}