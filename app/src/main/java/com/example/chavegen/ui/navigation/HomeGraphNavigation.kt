package com.example.chavegen.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.example.chavegen.ui.screens.CadastrarScreen
import com.example.chavegen.ui.screens.HomeScreen
import com.example.chavegen.ui.screens.SettingsScreen
import com.example.chavegen.ui.state.BottomBarScreen
import com.example.chavegen.ui.viewModel.HomeViewModel

fun NavGraphBuilder.homeGraph(
    navController: NavHostController
) {

    navigation(
        route = AppGraph.home.ROOT,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                onSignOut = {viewModel.signOut()},
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(route = BottomBarScreen.Cadastrar.route) {
            CadastrarScreen(
                navController = navController
            )
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(
                navController = navController
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