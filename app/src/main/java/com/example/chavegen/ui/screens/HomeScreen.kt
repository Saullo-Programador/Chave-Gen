package com.example.chavegen.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chavegen.ui.components.BottomBar
import com.example.chavegen.ui.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    onSignOut: () -> Unit,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val userName by viewModel.userName.collectAsState()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            HomeContent(
                onSignOut = onSignOut,
                userName = userName
            )
        }
    }
}



@Composable
fun HomeContent(
    onSignOut: () -> Unit,
    userName: String?
){

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen")
        Text("Bem-vindo ${userName ?: "Carregando..."}")
        Button(onClick = onSignOut) {
            Text("Sign Out")
        }
    }
}
