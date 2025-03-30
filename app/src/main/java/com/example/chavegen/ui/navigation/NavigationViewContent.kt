package com.example.chavegen.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chavegen.ui.components.BottomBar


@Composable
fun HomeViewContent(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { padding ->
        HomeNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}
