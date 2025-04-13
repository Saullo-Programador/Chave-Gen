package com.example.chavegen.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(message: String?) {
    val isVisible = !message.isNullOrBlank()
    AnimatedVisibility(visible = isVisible) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
        ) {
            Text(
                text = message ?: "",
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
        }
    }
}
