package com.example.chavegen.ui.viewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chavegen.authentication.FirebaseAuthRepository
import com.example.chavegen.ui.state.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
): ViewModel(){

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = {email ->
                    _uiState.update {
                        it.copy(email = email)
                    }
                },
                onPasswordChange = {password ->
                    _uiState.update {
                        it.copy(password = password)
                    }
                }
            )
        }
    }
    suspend fun signIn(){
        try {
            firebaseAuthRepository
                .signIn(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )
        }catch (e: Exception){
            Log.e("SignInViewModel", "signIn: ", e)
            _uiState.update {
                it.copy(erro = "Erro ao fazer login")
            }
            delay(3000)
            _uiState.update {
                it.copy(erro = null)
            }
        }
    }

    fun forgotPassword(){
        val email = _uiState.value.email

        if (email.isBlank()){
            mostrarMensagemTemporaria("Preencha o e-mail corretamente",false)
            return
        }

        viewModelScope.launch {
            val result = firebaseAuthRepository.sendPasswordResetEmail(email)

            result.onSuccess {
                mostrarMensagemTemporaria("E-mail de recuperação enviado com sucesso!", true)
            }.onFailure {
                mostrarMensagemTemporaria("Erro ao enviar e-mail de recuperação", false)
            }
        }

    }
    private fun mostrarMensagemTemporaria(mensagem: String, isResetEmailSent: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(
                erro = mensagem,
                isResetEmailSent = isResetEmailSent
            ) }
            delay(3000)
            _uiState.update { it.copy(
                erro = null,
            ) }
        }
    }

}