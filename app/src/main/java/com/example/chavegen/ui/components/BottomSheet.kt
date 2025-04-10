package com.example.chavegen.ui.components

import android.R.id.message
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chavegen.R
import androidx.core.net.toUri


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    siteName: String?,
    siteUrl: String?,
    siteUsername: String?,
    sitePassword: String?,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(siteName ?: "Não Encontrado", style = MaterialTheme.typography.titleLarge)
            IconButton(
                onClick = { onEdit() },
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Editar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            val clipboardManager = LocalClipboardManager.current
            val context = LocalContext.current

            SelectionContainer {
                Item(
                    tituloItem = "Email/Username",
                    valorItem = siteUsername ?: "Não Encontrado",
                    icon2Item = ImageVector.vectorResource(id = R.drawable.icon_copy),
                    onIcon2Click = {
                        clipboardManager.setText(AnnotatedString(siteUsername ?: ""))
                        Toast.makeText(context, "Email/Username copiado", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            Item(
                tituloItem = "Password",
                valorItem = sitePassword ?: "Não Encontrado",
                icon2Item = ImageVector.vectorResource(id = R.drawable.icon_copy),
                isPasswordField = true,
                onIcon2Click = {
                    clipboardManager.setText(AnnotatedString(sitePassword ?: ""))
                    Toast.makeText(context, "Senha copiada", Toast.LENGTH_SHORT).show()
                }
            )

            SelectionContainer {
                Item(
                    tituloItem = "Site",
                    valorItem = siteUrl ?: "Não Encontrado",
                    icon2Item = ImageVector.vectorResource(id = R.drawable.icon_acessar_link),
                    onIcon2Click = {
                        siteUrl?.let {
                            val intent = Intent(Intent.ACTION_VIEW, it.toUri())
                            context.startActivity(intent)
                            Toast.makeText(context, "Site aberto", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable { onDelete() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "deletar",
                    tint = Color.Red
                )
                Text(
                    text = "Deletar",
                    fontSize = 18.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun Item(
    tituloItem: String,
    valorItem: String,
    icon2Item: ImageVector? = null,
    onIcon2Click: () -> Unit = {},
    isPasswordField: Boolean = false,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val displayedValue = if (isPasswordField && !passwordVisible) "••••••••" else valorItem

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = tituloItem,
            color = Color.Gray,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (tituloItem == "Email/Username" || tituloItem == "Site") {
                SelectionContainer {
                    Text(
                        text = displayedValue,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                Text(
                    text = displayedValue,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ícone 1 (visibilidade)
                AnimatedVisibility(visible = isPasswordField) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        val icon = if (passwordVisible)
                            ImageVector.vectorResource(id = R.drawable.icon_visibility)
                        else
                            ImageVector.vectorResource(id = R.drawable.icon_visibility_off)

                        Icon(
                            imageVector = icon,
                            contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha"
                        )
                    }
                }

                // Ícone 2 (cópia ou outro)
                AnimatedVisibility(visible = icon2Item != null) {
                    IconButton(onClick = onIcon2Click) {
                        icon2Item?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = "Ação secundária"
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview( showBackground = true)
@Composable
fun BottomSheetDemoPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        BottomSheet(
            onEdit = {},
            onDelete = {},
            siteName = "Nome do Site",
            siteUrl = "Url do Site",
            siteUsername = "Usuario do Site",
            sitePassword = "Senha do Site",
        )
    }
}