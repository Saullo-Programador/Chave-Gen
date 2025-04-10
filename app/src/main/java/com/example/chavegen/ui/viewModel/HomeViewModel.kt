package com.example.chavegen.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chavegen.authentication.FirebaseAuthRepository
import com.example.chavegen.models.ItemLogin
import com.example.chavegen.repository.FireStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {

    private val _logins = MutableStateFlow<List<ItemLogin>>(emptyList())
    val logins: StateFlow<List<ItemLogin>> = _logins.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            firebaseAuthRepository.getCurrentUserId()?.let { userId ->
                buscarLogins(userId)
            } ?: run {
                _isLoading.value = false
            }
        }
    }

    fun buscarLogins(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            fireStoreRepository.getLogins(userId).collectLatest { lista ->
                _logins.value = lista
                kotlinx.coroutines.delay(200)
                _isLoading.value = false
            }
        }
    }


    fun deletarLogin(documentId: String) {
        viewModelScope.launch {
            firebaseAuthRepository.getCurrentUserId()?.let { userId ->
                fireStoreRepository.deletarLogin(userId, documentId)
                _logins.value = _logins.value.filter { it.documentId != documentId }
            }
        }
    }

    fun editarLogin(item: ItemLogin) {
        viewModelScope.launch {
            firebaseAuthRepository.getCurrentUserId()?.let { userId ->
                fireStoreRepository.editarLogin(userId, item)
                _logins.value = _logins.value.map {
                    if (it.documentId == item.documentId) item else it
                }
            }
        }
    }
}