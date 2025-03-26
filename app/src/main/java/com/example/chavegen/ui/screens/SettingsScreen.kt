package com.example.chavegen.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.chavegen.ui.components.BottomBar

@Composable
fun SettingsScreen(
    navController: NavHostController,
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)  // Passa o navController diretamente
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SettingsContent(
                telaName = "Settings Screen"
            )
        }
    }
}
@Composable
fun SettingsContent(
    telaName: String
){

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(telaName)
    }
}