package com.example.chavegen.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ErrorMessage(
    message: String?,
    durationMillis: Int = 3000,
) {
    val isVisible = !message.isNullOrBlank()
    val isSuccess = message?.contains("recuperação enviado", ignoreCase = true) == true
    val barColor = if (isSuccess) Color(0xFF4CAF50) else Color.Red


    var progress by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(message) {
        if (isVisible) {
            progress = 1f
            val steps = 30
            repeat(steps) {
                delay(durationMillis.toLong() / steps)
                progress -= 1f / steps
            }
            progress = 0f
        }
    }

    AnimatedVisibility(visible = isVisible) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(barColor),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = message ?: "",
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                    Icon(
                        imageVector = if (isSuccess) Icons.Default.Check else Icons.Default.Clear,
                        contentDescription = if (isSuccess) "Success" else "Error",
                        tint = Color.White,
                        modifier = Modifier.padding(16.dp),
                    )

                }
            }
        }
    }
}
