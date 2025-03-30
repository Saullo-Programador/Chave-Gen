package com.example.chavegen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBarComponent(
    title: String,
    modifier: Modifier = Modifier,
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
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            letterSpacing = 2.sp,
            color = contentColor,
            modifier = Modifier.weight(1f).padding(start = 5.dp),
        )

        Box(modifier = Modifier.size(40.dp)) {
            trailingIcon?.invoke()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBarComponent(
        title = "Cadastro",

        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        roundedCornerSize = 16
    )
}
