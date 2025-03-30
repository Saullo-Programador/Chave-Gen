package com.example.chavegen.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chavegen.ui.viewModel.RegisterViewModel

@Composable
fun GerarSenhaComponent(
    onDismissRequest: () -> Unit,
    onUsePassword: (String) -> Unit,
    viewModel: RegisterViewModel
) {
    val passwordState by viewModel.uiStateGeneratedPassword.collectAsState()
    val generatedPassword by viewModel.generatedPassword.collectAsState()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Gerar Senha")
                IconButton(
                    onClick = onDismissRequest,
                    Modifier.size(30.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Red,
                    )
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Fechar")
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = passwordState.length.toString(),
                    onValueChange = { newValue ->
                        viewModel.onPasswordOptionsChange(
                            passwordState.copy(length = newValue.toIntOrNull() ?: 12)
                        )
                    },
                    label = { Text("Quantidade de Caracteres") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(150.dp).height(55.dp)
                )

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = passwordState.useUppercase,
                            onCheckedChange = { viewModel.onPasswordOptionsChange(passwordState.copy(useUppercase = it)) }
                        )
                        Text(text = "Letras Maiúsculas", fontSize = 16.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = passwordState.useLowercase,
                            onCheckedChange = { viewModel.onPasswordOptionsChange(passwordState.copy(useLowercase = it)) }
                        )
                        Text(text = "Letras Minúsculas", fontSize = 16.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = passwordState.useNumbers,
                            onCheckedChange = { viewModel.onPasswordOptionsChange(passwordState.copy(useNumbers = it)) }
                        )
                        Text(text = "Números", fontSize = 16.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = passwordState.useSymbols,
                            onCheckedChange = { viewModel.onPasswordOptionsChange(passwordState.copy(useSymbols = it)) }
                        )
                        Text(text = "Símbolos", fontSize = 16.sp)
                    }
                }
                Text(
                    text = "Senha Gerada: $generatedPassword",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            Column {
                Button(
                    onClick = { viewModel.generatePassword() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Gerar Senha")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (generatedPassword.isNotEmpty()) {
                            onUsePassword(generatedPassword)
                            onDismissRequest()
                        }
                    },
                    enabled = generatedPassword.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Usar Senha")
                }
            }
        }
    )
}


@Composable
fun DialogGerarSenha(
    closeAlertDialog: () -> Unit,
    onUsePassword: (String) -> Unit,
    viewModel: RegisterViewModel
) {
    GerarSenhaComponent(
        onDismissRequest = { closeAlertDialog() },
        onUsePassword = onUsePassword,
        viewModel = viewModel
    )
}
