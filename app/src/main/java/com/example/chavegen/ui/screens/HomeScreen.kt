package com.example.chavegen.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chavegen.ui.components.FloatingButton
import com.example.chavegen.ui.components.ItemLogin
import com.example.chavegen.ui.components.TopBarComponent
import com.example.chavegen.ui.components.view.LoadingView
import com.example.chavegen.ui.components.view.TextView
import com.example.chavegen.ui.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onAddClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onViewLoginItem: () -> Unit = {},
    onEditTask: () -> Unit = {},
    onDeleteTask: () -> Unit = {}
) {
    HomeView(
        onAddClick = onAddClick,
        onSettingsClick = onSettingsClick,
        viewModel = viewModel,
        onViewLoginItem = onViewLoginItem,
        onEditTask = onEditTask,
        onDeleteTask = onDeleteTask
    )
}

@Composable
fun HomeView(
    onAddClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    viewModel: HomeViewModel,
    onViewLoginItem: () -> Unit = {},
    onEditTask: () -> Unit = {},
    onDeleteTask: () -> Unit = {}
){
    Scaffold(
        containerColor = MaterialTheme. colorScheme.surface,
        floatingActionButton = {
            FloatingButton(
                onClick = onAddClick,
                icon = Icons.Default.Add,
                contentDescription = "Adicionar uma nova senha ao gerenciador"
            )
        },
        topBar = {
            TopBarComponent(
                title = "Chave Gen",
                trailingIcon = Icons.Default.Settings,
                onTrailingClick = onSettingsClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeContent(
                viewModel = viewModel,
                onViewLoginItem = onViewLoginItem,
                onEditTask = onEditTask,
                onDeleteTask = onDeleteTask
            )
        }
    }
}

@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    onViewLoginItem: () -> Unit = {},
    onEditTask: () -> Unit = {},
    onDeleteTask: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeList(
            viewModel = viewModel,
            onViewLoginItem = {onViewLoginItem},
            onEditTask = {onEditTask},
            onDeleteTask = {onDeleteTask}
        )
    }
}


@Composable
fun HomeList(
    viewModel: HomeViewModel,
    onViewLoginItem: () -> Unit = {},
    onEditTask: () -> Unit = {},
    onDeleteTask: () -> Unit = {}

) {
    val logins by viewModel.logins.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    when {
        isLoading -> {
            LoadingView()
        }
        logins.isEmpty() -> {
            TextView(text = "Nenhum login salvo ainda.")
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(logins.size) { index ->
                    val login = logins[index]
                    ItemLogin(
                        taskName = login.siteName ?: "",
                        taskDescription = login.siteUrl ?: "",
                        onDeleteTask = onDeleteTask,
                        onEditTask = onEditTask,
                        viewLoginItem = onViewLoginItem
                    )
                }
            }
        }
    }
}

