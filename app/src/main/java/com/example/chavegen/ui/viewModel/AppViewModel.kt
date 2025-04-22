package com.example.chavegen.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.chavegen.authentication.FirebaseAuthRepository
import com.example.chavegen.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class AppState(
    val user: User? = null,
    val isInitLoading: Boolean = true,
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class AppViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state = _state
        .combine(firebaseAuthRepository.currentUser) { appState, authResult ->
            // Verifica se o usuário está autenticado
            val isUserLoggedIn = authResult.currentUser != null
            val user = authResult.currentUser?.email?.let { User(it) }

            // Atualiza o estado com o usuário e a flag de carregamento
            appState.copy(
                user = user,
                isInitLoading = authResult.isInitLoading,
                isLoggedIn = isUserLoggedIn // Flag de autenticação
            )
        }

}