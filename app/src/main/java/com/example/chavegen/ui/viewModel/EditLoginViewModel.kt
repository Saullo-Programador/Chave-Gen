package com.example.chavegen.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chavegen.authentication.FirebaseAuthRepository
import com.example.chavegen.models.ItemLogin
import com.example.chavegen.repository.FireStoreRepository
import com.example.chavegen.ui.state.EditLoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditLoginViewModel @Inject constructor(
    private val fireStoreRepository: FireStoreRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditLoginUiState())
    val uiState = _uiState.asStateFlow()

    fun editarLogin() {
        val userId = firebaseAuthRepository.getCurrentUserId() ?: return
        val state = _uiState.value

        val itemLoginEditado = ItemLogin(
            documentId = state.documentId,
            userId = userId,
            siteName = state.siteName,
            siteUrl = state.siteUrl,
            siteUser = state.siteUser,
            sitePassword = state.sitePassword
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                fireStoreRepository.editarLogin(userId, itemLoginEditado)
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Erro ao editar login: ${e.message}") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }


    fun loadLoginFromFirestore(documentId: String) {
        val userId = firebaseAuthRepository.getCurrentUserId() ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val itemLogin = fireStoreRepository.getLoginById(userId, documentId)
                itemLogin?.let {
                    _uiState.update { state ->
                        state.copy(
                            documentId = it.documentId,
                            siteName = it.siteName ?: "",
                            siteUrl = it.siteUrl ?: "",
                            siteUser = it.siteUser ?: "",
                            sitePassword = it.sitePassword ?: "",
                            onSiteNameChange = { newValue ->
                                _uiState.update { it.copy(siteName = newValue) }
                            },
                            onSiteUrlChange = { newValue ->
                                _uiState.update { it.copy(siteUrl = newValue) }
                            },
                            onSiteUserChange = { newValue ->
                                _uiState.update { it.copy(siteUser = newValue) }
                            },
                            onSitePasswordChange = { newValue ->
                                _uiState.update { it.copy(sitePassword = newValue) }
                            },
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                } ?: _uiState.update {
                    it.copy(errorMessage = "Login não encontrado.", isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Erro ao carregar login: ${e.message}", isLoading = false)
                }
            }
        }
    }
}
