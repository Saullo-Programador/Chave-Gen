package com.example.chavegen.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chavegen.ui.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    onSignOut: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val userName by viewModel.userName.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            onSignOut = onSignOut,
            userName = userName
        )
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
        Text(
            text = "Home Screen",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Bem-vindo ${userName ?: "Carregando..."}",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
        Button(onClick = onSignOut) {
            Text("Sign Out")
        }
    }
}
