package com.example.chavegen.ui.viewModel


import android.util.Log
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

    private val _signUpIsSuccessful = MutableSharedFlow<Boolean>()
    val signUpIsSuccessful = _signUpIsSuccessful.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

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
                _uiState.update {
                    it.copy(
                        error = "Preencha todos os campos"
                    )
                }
                delay(3000)
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
                return@launch
            }
            if (state.password != state.confirmPassword) {
                _uiState.update {
                    it.copy(
                        error = "As senhas não coincidem"
                    )
                }

                return@launch
            }
            _isLoading.value = true
            try {
                firebaseAuthRepository
                    .signUp(
                        email = _uiState.value.email,
                        password = _uiState.value.password,
                        userName = _uiState.value.user
                    )
                _signUpIsSuccessful.emit(true)
            } catch (e: Exception) {
                Log.e("SignUpViewModel", "signUp: ", e)
                _uiState.update {
                    it.copy(
                        error = "Erro ao cadastrar o usuário"
                    )
                }
                delay(3000)
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

}