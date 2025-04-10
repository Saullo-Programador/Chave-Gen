// SplashScreen.kt
package com.example.chavegen.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chavegen.ui.viewModel.AppViewModel
import com.example.chavegen.ui.viewModel.AppState
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToAuth: () -> Unit
) {
    val viewModel: AppViewModel = hiltViewModel()
    val appState by viewModel.state.collectAsState(initial = AppState())

    LaunchedEffect(appState.isInitLoading) {
        if (!appState.isInitLoading) {
            delay(2000)
            if (appState.user != null) {
                onNavigateToHome()
            } else {
                onNavigateToAuth()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = com.example.chavegen.R.drawable.logo1),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )
            Text(text = "🔐 ChaveGen", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}
