package com.example.chavegen.ui.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.chavegen.ui.screens.SplashScreen

fun NavGraphBuilder.splashScreen() {
    composable(AppGraph.splash.SPLASH) {
        SplashScreen()
    }
}