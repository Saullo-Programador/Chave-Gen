package com.example.chavegen.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.example.chavegen.ui.screens.HomeScreen
import com.example.chavegen.ui.screens.RegisterScreen
import com.example.chavegen.ui.screens.SettingsScreen
import com.example.chavegen.ui.screens.SignInScreen
import com.example.chavegen.ui.screens.SignUpScreen
import com.example.chavegen.ui.viewModel.HomeViewModel
import com.example.chavegen.ui.viewModel.RegisterViewModel
import com.example.chavegen.ui.viewModel.SignInViewModel
import com.example.chavegen.ui.viewModel.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = AppGraph.initial.ROOT,
        startDestination = AppGraph.auth.ROOT
    ) {
        authNavGraph(navController = navController)
        composable(route = AppGraph.home.ROOT) {
            HomeViewContent()
        }
    }
}

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = AppGraph.auth.ROOT,
        startDestination = AppGraph.auth.SIGN_IN
    ) {
        composable(route = AppGraph.auth.SIGN_IN) {
            val viewModel: SignInViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val scope = rememberCoroutineScope()
            SignInScreen(
                onSignInClick = {
                    scope.launch {
                        viewModel.signIn()
                    }
                },
                onSignUpClick = {
                    navController.navigate(AppGraph.auth.SIGN_UP)
                },
                uiState = uiState,
            )
        }
        composable(route = AppGraph.auth.SIGN_UP) {
            val viewModel: SignUpViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val scope = rememberCoroutineScope()
            val signUpIsSuccessful by viewModel.signUpIsSuccessful.collectAsState(false)

            LaunchedEffect(signUpIsSuccessful) {
                if (signUpIsSuccessful) {
                    navOptions {
                        popUpTo(AppGraph.auth.ROOT) { inclusive = true }
                    }
                }
            }
            SignUpScreen(
                onSignUpClick = {
                    scope.launch {
                        viewModel.signUp()
                    }
                },
                onSignInClick = {
                    navController.navigate(AppGraph.auth.SIGN_IN)
                },
                uiState = uiState
            )
        }
    }
}


fun NavHostController.navigateToAuthGraph(
    navOptions: NavOptions? = navOptions {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
) {
    navigate(AppGraph.auth.ROOT, navOptions)
}


@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = AppGraph.home.ROOT,
        startDestination = AppGraph.home.HOME
    ) {
        composable(route = AppGraph.home.HOME) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                onSignOut = { viewModel.signOut() },
                viewModel = viewModel
            )
        }
        composable(route = AppGraph.home.REGISTER) {
            val viewModel: RegisterViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            RegisterScreen(uiState = uiState, viewModel = viewModel)
        }
        composable(route = AppGraph.home.SETTINGS) {
            SettingsScreen()
        }
    }
}


fun NavHostController.navigateToHomeGraph(
    navOptions: NavOptions? = navOptions {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
) {
    navigate(AppGraph.home.ROOT, navOptions)
}