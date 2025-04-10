package com.example.chavegen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import com.example.chavegen.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ItemLogin(
    modifier: Modifier = Modifier,
    taskName: String,
    taskDescription: String = "",
    onDeleteTask: () -> Unit = {},
    onEditTask: () -> Unit = {},
    viewLoginItem: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .clickable(onClick = viewLoginItem),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {

                }
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = taskName,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (taskDescription.isNotEmpty()) {
                        Text(
                            text = taskDescription,
                            fontSize = 15.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_more_horiz),
                        contentDescription = "Menu Opções",
                        Modifier.size(30.dp)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Editar",
                                fontSize = 17.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "Editar",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        onClick = {
                            expanded = false
                            onEditTask()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Excluir",
                                fontSize = 17.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Excluir",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        onClick = {
                            expanded = false
                            onDeleteTask()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsItem(
    text: String,
    itemText: String? = null,
    textButton: String? = null,
    optionClickItem: Int = 1,
    switchState: Boolean = false,
    onItemClick: () -> Unit = {},
    colorTextButton: Color = MaterialTheme.colorScheme.onBackground,
    containerColorButton: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            fontSize = 19.sp,
        )

        when (optionClickItem) {
            1 -> {
                Switch(
                    checked = switchState,
                    onCheckedChange = { onItemClick() },
                    colors = SwitchDefaults.colors(
                        uncheckedBorderColor = Color.Transparent,

                        checkedThumbColor = Color.White,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        checkedBorderColor = Color.Transparent

                    )
                )
            }
            2 -> {
                var expanded by remember { mutableStateOf(false) }
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            painter = painterResource(R.drawable.icon_more_horiz),
                            contentDescription = "Menu Opções",
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Editar") },
                            onClick = {
                                expanded = false
                                onItemClick()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Excluir") },
                            onClick = {
                                expanded = false
                                onItemClick()
                            }
                        )
                    }
                }
            }
            3 -> {
                Button(
                    onClick = onItemClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = containerColorButton
                    )
                ) {
                    Text(
                        text = textButton ?: "Clique aqui",
                        color = colorTextButton,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
            }
            4 -> {
                Text(
                    text = itemText ?: "Clique aqui",
                    color = colorTextButton,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                    modifier = Modifier.clickable { onItemClick() }
                )
            }
            5 -> {
                Text(
                    text = itemText ?: "Carregando...",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemTaskPreview() {
    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ItemLogin(
            taskName = "Nome da Tarefa",
            taskDescription = "Descrição da tarefa",
            onDeleteTask = {},
            onEditTask = {}
        )
    }
}