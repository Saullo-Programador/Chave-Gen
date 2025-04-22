package com.example.chavegen.ui.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chavegen.authentication.FirebaseAuthRepository
import com.example.chavegen.ui.state.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        object NavigateToLogin : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onUserChange = {user ->
                    _uiState.update {
                        it.copy ( user = user )
                    }
                },
                onEmailChange = {email ->
                    _uiState.update {
                        it.copy ( email = email )
                    }
                },
                onPasswordChange = {password ->
                    _uiState.update {
                        it.copy ( password = password )
                    }
                },
                onConfirmPasswordChange = {password  ->
                    _uiState.update {
                        it.copy ( confirmPassword = password )
                    }
                }
            )
        }
    }

    fun signUp() {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.user.isBlank() || state.email.isBlank() || state.password.isBlank() || state.confirmPassword.isBlank()) {
                showErrorAndClear("Preencha todos os campos")
                return@launch
            }

            if (state.password != state.confirmPassword) {
                showErrorAndClear("As senhas não coincidem")
                return@launch
            }

            _isLoading.value = true
            try {
                val result = firebaseAuthRepository.signUp(
                    email = state.email,
                    password = state.password,
                    userName = state.user
                )
                if (result) {
                    _eventFlow.emit(UiEvent.NavigateToLogin)
                    firebaseAuthRepository.signOut()
                } else {
                    showErrorAndClear("Erro ao cadastrar o usuário")
                }
            } catch (e: Exception) {
                showErrorAndClear("Erro ao tentar cadastrar o usuário: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
    private suspend fun showErrorAndClear(message: String) {
        _eventFlow.emit(UiEvent.ShowError(message))
        delay(3000)
    }

}