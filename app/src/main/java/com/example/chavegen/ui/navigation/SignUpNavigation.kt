package com.example.chavegen.ui.navigation


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.chavegen.ui.screens.SignUpScreen
import com.example.chavegen.ui.viewModel.SignUpViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.signUpScreen(
    onNavigationToSignIn: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    composable(AppGraph.auth.SIGN_UP) {
        val viewModel: SignUpViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        val signUpIsSuccessful by viewModel.signUpIsSuccessful.collectAsState(false)

        LaunchedEffect (signUpIsSuccessful) {
            if (signUpIsSuccessful) {
                onNavigationToSignIn()
            }
        }

        SignUpScreen(
            uiState = uiState,
            onSignUpClick = {
                scope.launch {
                    viewModel.signUp()
                }
            },
            onSignInClick = onNavigateToSignIn
        )
    }
}

fun NavHostController.navigateToSignUp() {
    navigate(AppGraph.auth.SIGN_UP)
}