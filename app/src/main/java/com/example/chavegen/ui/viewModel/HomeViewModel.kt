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
                _isLoading.value = false  // Se não houver usuário, define como carregamento concluído
            }
        }
    }

    fun buscarLogins(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true  // Começa o carregamento
            fireStoreRepository.getLogins(userId).collectLatest { lista ->
                _logins.value = lista
                _isLoading.value = false  // Finaliza o carregamento após obter os dados
            }
        }
    }
}