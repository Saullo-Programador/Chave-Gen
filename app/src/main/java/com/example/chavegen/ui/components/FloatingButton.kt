package com.example.chavegen.ui.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun FloatingButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String?,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    iconTint: Color = Color.White
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = backgroundColor,
        contentColor = iconTint
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint
        )
    }
}