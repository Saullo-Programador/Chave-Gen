package com.example.chavegen.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DialogComponent(
    titleDelete: String = "Deletar Login",
    messageDelete: String = "Tem certeza que deseja deletar este login?" ,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = titleDelete,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = messageDelete,
                maxLines = 3,
                softWrap = true,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        dismissButton = {
            CustomButton(
                onClick = onDismiss,
                text = "Cancelar",
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent
                ),
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(15))
                    .width(100.dp),
                fontColor = MaterialTheme.colorScheme.primary,
                cornerRadius = 15
            )

        },
        confirmButton = {
            CustomButton(
                onClick = onConfirm,
                text = "Deletar",
                modifier = Modifier
                    .width(100.dp),
                cornerRadius = 15

            )
        }
    )
}
