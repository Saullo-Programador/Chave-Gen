package com.example.chavegen.ui.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.example.chavegen.ui.screens.EditLoginScreen
import com.example.chavegen.ui.screens.ForgotScreen
import com.example.chavegen.ui.screens.HomeScreen
import com.example.chavegen.ui.screens.RegisterMain
import com.example.chavegen.ui.screens.SettingsScreen
import com.example.chavegen.ui.screens.SignInScreen
import com.example.chavegen.ui.screens.SignUpScreen
import com.example.chavegen.ui.screens.SplashScreen
import com.example.chavegen.ui.viewModel.EditLoginViewModel
import com.example.chavegen.ui.viewModel.HomeViewModel
import com.example.chavegen.ui.viewModel.RegisterViewModel
import com.example.chavegen.ui.viewModel.SettingsViewModel
import com.example.chavegen.ui.viewModel.SignInViewModel
import com.example.chavegen.ui.viewModel.SignUpViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    onThemeToggle: () -> Unit
) {
    NavHost(
        navController = navController,
        route = AppGraph.initial.ROOT,
        startDestination = AppGraph.initial.SPLASH,
        enterTransition = { fadeIn(animationSpec = tween(
            durationMillis = 1000,easing = FastOutSlowInEasing
        )) },
        exitTransition = { fadeOut(animationSpec = tween(
            durationMillis = 1000, easing = FastOutSlowInEasing
        )) },
    ) {
        composable(route = AppGraph.initial.SPLASH) {
            SplashScreen()
        }
        authNavGraph(navController = navController)
        homeNavGraph( navController = navController, onThemeToggle = onThemeToggle)
    }
}


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = AppGraph.auth.ROOT,
        startDestination = AppGraph.auth.SIGN_IN,

    ) {
        composable(
            route = AppGraph.auth.SIGN_IN,
            enterTransition = { slideInHorizontally(initialOffsetX = { 2000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -2000 }) },
        ) {
            val viewModel: SignInViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()
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
                onForgotPasswordClick = {
                    navController.navigate(AppGraph.auth.FORGOT)
                },
                uiState = uiState,
                isLoading = isLoading
            )
        }
        composable(
            route = AppGraph.auth.SIGN_UP,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
        ) {
            val viewModel: SignUpViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val scope = rememberCoroutineScope()
            val isLoading by viewModel.isLoading.collectAsState()
            val signUpIsSuccessful by viewModel.signUpIsSuccessful.collectAsState(false)

            LaunchedEffect(signUpIsSuccessful) {
                if (signUpIsSuccessful) {
                    delay(500)
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
                uiState = uiState,
                isLoading = isLoading
            )
        }

        composable (route = AppGraph.auth.FORGOT){
            val viewModel: SignInViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val scope = rememberCoroutineScope()
            val currentIsResetEmailSent by rememberUpdatedState(uiState.isResetEmailSent)

            LaunchedEffect(currentIsResetEmailSent) {
                if (currentIsResetEmailSent) {
                    delay(3500)
                    navController.navigate(AppGraph.auth.ROOT) {
                        popUpTo(AppGraph.auth.ROOT) {
                            inclusive = true
                        }
                    }
                }
            }

            ForgotScreen(
                uiState = uiState,
                onForgotClick = {
                    scope.launch {
                        viewModel.forgotPassword()
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
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


fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    onThemeToggle: () -> Unit
){
    navigation(
        route = AppGraph.home.ROOT,
        startDestination = AppGraph.home.HOME
    ) {
        composable(route = AppGraph.home.HOME) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onAddClick = {
                    navController.navigate(AppGraph.home.REGISTER)
                }  ,
                onSettingsClick = {
                    navController.navigate(AppGraph.home.SETTINGS)
                },
                onEditItem = { documentId ->
                    navController.navigate("${AppGraph.home.EDIT_LOGIN_BASE}/$documentId")
                }
            )
        }
        composable(route = AppGraph.home.REGISTER) {
            val viewModel: RegisterViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            RegisterMain(
                uiState = uiState,
                viewModel = viewModel,
                onSaveSuccess = {
                    navController.navigate(AppGraph.home.HOME)
                },
                onBackClick = {
                    navController.navigate(AppGraph.home.HOME)
                }
            )
        }
        composable(route = AppGraph.home.SETTINGS) {
            val viewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(
                onThemeToggle = onThemeToggle,
                onSignOut = { viewModel.signOut() },
                onBackClick = {
                    navController.navigate(AppGraph.home.HOME)
                }
            )
        }
        composable(
            route = AppGraph.home.EDIT_LOGIN,
            arguments = listOf(navArgument("documentId") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { backStackEntry ->
            val viewModel: EditLoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val documentId = backStackEntry.arguments?.getString("documentId") ?: ""

            LaunchedEffect(documentId) {
                viewModel.loadLoginFromFirestore(documentId)
            }

            EditLoginScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                state = uiState
            )
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