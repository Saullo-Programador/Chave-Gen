package com.example.chavegen.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chavegen.authentication.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
): ViewModel() {
    private val _userName = MutableStateFlow<String?>(null)
    val userName = _userName.asStateFlow()

    init {
        viewModelScope.launch {
            val name = firebaseAuthRepository.getUserName()
            _userName.value = name
        }
    }

    fun signOut() {
        firebaseAuthRepository.signOut()
    }
}
