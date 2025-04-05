package com.example.chavegen.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.chavegen.authentication.FirebaseAuthRepository
import com.example.chavegen.models.ItemLogin
import com.example.chavegen.repository.FireStoreRepository
import com.example.chavegen.ui.state.EditLoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditLoginViewModel @Inject constructor(
    private val fireStoreRepository: FireStoreRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(EditLoginUiState())
    val uiState = _uiState.asStateFlow()

    fun editarLogin() {
        val userId = firebaseAuthRepository.getCurrentUserId()
        val state = _uiState.value

        val itemLoginEditado = ItemLogin(
            documentId = state.documentId,
            userId = userId,
            siteName = state.siteName,
            siteUrl = state.siteUrl,
            siteUser = state.siteUser,
            sitePassword = state.sitePassword
        )
        fireStoreRepository.editarLogin(userId!!, itemLoginEditado)
    }

    fun loadLoginFromFirestore(documentId: String) {
        val userId = firebaseAuthRepository.getCurrentUserId() ?: return

        fireStoreRepository.getLoginById(userId, documentId) { itemLogin ->
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
                        }
                    )
                }
            }
        }
    }

}