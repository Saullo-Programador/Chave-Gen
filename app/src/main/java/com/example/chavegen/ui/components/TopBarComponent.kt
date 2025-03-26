package com.example.chavegen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopBarComponent(
    title: String,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White,
    roundedCornerSize: Int = 26
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(bottomStart = roundedCornerSize.dp, bottomEnd = roundedCornerSize.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Ícone da esquerda (se existir)
        Box(modifier = Modifier.size(40.dp)) {
            leadingIcon?.invoke()
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = contentColor,
            modifier = Modifier.weight(1f),
        )

        Box(modifier = Modifier.size(40.dp)) {
            trailingIcon?.invoke()
        }
    }
}
