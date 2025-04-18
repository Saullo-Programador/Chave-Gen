package com.example.chavegen.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chavegen.authentication.FirebaseAuthRepository
import com.example.chavegen.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppState(
    val user: User? = null,
    val isInitLoading: Boolean = true
)
@HiltViewModel
class AppViewModel @Inject constructor(
    firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state: StateFlow<AppState> = _state
        .combine(firebaseAuthRepository.currentUser) { appState, authResult ->
            val user = authResult.currentUser?.email?.let { User(it) }
            appState.copy(
                user = user,
                isInitLoading = authResult.isInitLoading
            )
        } as StateFlow<AppState>
}
