package com.example.chavegen.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chavegen.ui.components.SettingsItem
import com.example.chavegen.ui.components.TopBarComponent
import com.example.chavegen.ui.viewModel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onThemeToggle: () -> Unit,
    onSignOut: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val userName by viewModel.userName.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarComponent(
            title = "Settings",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = onBackClick
        )
        SettingsContent(
            onThemeToggle = onThemeToggle,
            onSignOut = onSignOut,
            userName = userName
        )
    }
}

@Composable
fun SettingsContent(
    onThemeToggle: () -> Unit,
    onSignOut: () -> Unit = {},
    userName: String?
) {
    var switchState by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .padding(top = 8.dp)
        )
        SettingsItem(
            text = "Usuario",
            itemText = " ${userName ?: "Carregando..."}",
            optionClickItem = 5,
            onItemClick = {}
        )
        SettingsItem(
            text = "Dark Mode",
            optionClickItem = 1,
            switchState = switchState,
            onItemClick = {
                switchState = !switchState
                onThemeToggle()
            }
        )
        SettingsItem(
            text = "Sair da conta",
            optionClickItem = 4,
            itemText = "Sair",
            colorTextButton = Color.Red,
            onItemClick = {
                onSignOut()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(onThemeToggle = {})
}

