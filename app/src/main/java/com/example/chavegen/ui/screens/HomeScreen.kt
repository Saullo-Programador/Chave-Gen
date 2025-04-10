package com.example.chavegen.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chavegen.models.ItemLogin
import com.example.chavegen.ui.components.BottomSheet
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
    onEditItem: (String) -> Unit,
) {
    HomeView(
        onAddClick = onAddClick,
        onSettingsClick = onSettingsClick,
        viewModel = viewModel,
        onEditItem = onEditItem,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    onAddClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    viewModel: HomeViewModel,
    onEditItem: (String) -> Unit,
){
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedLogin by remember { mutableStateOf<ItemLogin?>(null) }


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
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeContent(
                viewModel = viewModel,
                onViewLoginItem = {
                    selectedLogin = it
                    showSheet = true
                },
                onEditItem = onEditItem,
            )
            if (showSheet && selectedLogin != null) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showSheet = false
                        selectedLogin = null
                    },
                    sheetState = sheetState
                ) {
                    BottomSheet(
                        onEdit = {
                            showSheet = false
                            selectedLogin?.let {
                                viewModel.editarLogin(it)
                                onEditItem(it.documentId)
                            }
                        },
                        onDelete = {
                            showSheet = false
                            selectedLogin?.let {
                                viewModel.deletarLogin(it.documentId)
                            }
                        },
                        siteName = selectedLogin?.siteName ?: "",
                        siteUsername = selectedLogin?.siteUser ?: "",
                        sitePassword = selectedLogin?.sitePassword ?: "",
                        siteUrl = selectedLogin?.siteUrl ?: "",
                    )
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    onViewLoginItem: (ItemLogin) -> Unit,
    onEditItem: (String) -> Unit,
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
            onViewLoginItem = onViewLoginItem,
            onEditItem = onEditItem,
        )
    }
}

@Composable
fun HomeList(
    viewModel: HomeViewModel,
    onViewLoginItem: (ItemLogin) -> Unit,
    onEditItem: (String) -> Unit = {},
) {
    val logins by viewModel.logins.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Crossfade(targetState = isLoading) { loading ->
        when {
            loading -> {
                LoadingView()
            }
            logins.isEmpty() -> {
                TextView(text = "Nenhum login salvo ainda.")
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(logins.size) { index ->
                        val login = logins[index]
                        ItemLogin(
                            taskName = login.siteName ?: "",
                            taskDescription = login.siteUrl ?: "",
                            onDeleteTask = {
                                viewModel.deletarLogin(login.documentId)
                            },
                            onEditTask = {
                                viewModel.editarLogin(login)
                                onEditItem(login.documentId)
                            },
                            viewLoginItem = {
                                onViewLoginItem(login)
                            }
                        )
                    }
                }
            }
        }
    }
}
