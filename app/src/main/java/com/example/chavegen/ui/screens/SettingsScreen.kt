package com.example.chavegen.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chavegen.ui.components.SettingsItem
import com.example.chavegen.ui.components.TopBarComponent

@Composable
fun SettingsScreen(
    onThemeToggle: () -> Unit,
    onSignOut: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TopBarComponent(
            title = "Settings",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = onBackClick
        )
        SettingsContent(
            onThemeToggle = onThemeToggle,
            onSignOut = onSignOut
        )
    }
}

@Composable
fun SettingsContent(
    onThemeToggle: () -> Unit,
    onSignOut: () -> Unit = {}
) {
    var switchState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            optionClickItem = 3,
            textButton = "Sair",
            colorTextButton = Color.White,
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

