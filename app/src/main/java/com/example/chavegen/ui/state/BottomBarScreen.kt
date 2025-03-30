package com.example.chavegen.ui.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route : String,
    val title : String,
    val icon : ImageVector,
    val iconFocused : ImageVector
){
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home,
        iconFocused = Icons.Filled.Home
    )

    object Register : BottomBarScreen(
        route = "register",
        title = "Register",
        icon = Icons.Default.Add,
        iconFocused = Icons.Filled.Add
    )

    object Settings: BottomBarScreen(
        route = "settings",
        title = "Settings",
        icon = Icons.Outlined.Settings,
        iconFocused = Icons.Default.Settings
    )
}