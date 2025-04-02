package com.example.chavegen.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chavegen.ui.components.FloatingButton
import com.example.chavegen.ui.components.TopBarComponent
import com.example.chavegen.ui.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onAddClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val userName by viewModel.userName.collectAsState()
    HomeContent(
        userName = userName,
        onAddClick = onAddClick,
        onSettingsClick = onSettingsClick
    )
}



@Composable
fun HomeContent(
    userName: String?,
    onAddClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
){
    Scaffold(
        floatingActionButton = {
            FloatingButton(
                onClick = onAddClick,
                icon = Icons.Default.Add,
                contentDescription = "Adicionar uma nova senha ao gerenciador"
            )
        },
        topBar = {
            TopBarComponent(
                title = "Chave Gen",
                trailingIcon = Icons.Default.Settings,
                onTrailingClick = onSettingsClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bem-vindo ${userName ?: "Carregando..."}",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
