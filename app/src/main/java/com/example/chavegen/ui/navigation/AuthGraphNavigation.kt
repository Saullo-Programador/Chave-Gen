package com.example.chavegen.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation


fun NavGraphBuilder.authGraph(
    onNavigateToSignUp: () -> Unit,
    onNavigateToSignIn: (NavOptions) -> Unit
) {
    navigation(
        route = AppGraph.auth.ROOT,
        startDestination = AppGraph.auth.SIGN_IN
    ) {
        signInScreen(
            onNavigateToSignUp = onNavigateToSignUp,
        )
        signUpScreen(
            onNavigationToSignIn = {
                onNavigateToSignIn(navOptions {
                    popUpTo(AppGraph.auth.ROOT){inclusive = true}
                })
            },
            onNavigateToSignIn ={
                onNavigateToSignIn(navOptions {
                    popUpTo(AppGraph.auth.ROOT){inclusive = true}
                })
            }
        )
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