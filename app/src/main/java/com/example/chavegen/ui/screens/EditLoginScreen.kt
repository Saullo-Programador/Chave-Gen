package com.example.chavegen.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chavegen.authentication.FirebaseAuthRepository
import com.example.chavegen.repository.FireStoreRepository
import com.example.chavegen.ui.components.CustomButton
import com.example.chavegen.ui.components.CustomTextField
import com.example.chavegen.ui.components.TopBarComponent
import com.example.chavegen.ui.components.view.LoadingView
import com.example.chavegen.ui.state.EditLoginUiState
import com.example.chavegen.ui.viewModel.EditLoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EditLoginScreen(
    viewModel: EditLoginViewModel,
    onNavigateBack: () -> Unit,
    state: EditLoginUiState
) {
    if (state.siteName.isEmpty()) {
        LoadingView()
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarComponent(
                title = "Editar Login",
                onNavigationClick = onNavigateBack,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,

            )
            EditLoginContent(
                viewModel = viewModel,
                onNavigateBack = onNavigateBack,
                state = state
            )
        }
    }
}

@Composable
fun EditLoginContent(
    viewModel: EditLoginViewModel,
    onNavigateBack: () -> Unit,
    state: EditLoginUiState
) {
    Column(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        CustomTextField(
            value = state.siteName,
            onValueChange = state.onSiteNameChange,
            label = "Nome do Site"
        )
        CustomTextField(
            value = state.siteUrl,
            onValueChange = state.onSiteUrlChange,
            label = "URL do Site"
        )

        CustomTextField(
            value = state.siteUser,
            onValueChange = state.onSiteUserChange,
            label = "Usuário"
        )

        CustomTextField(
            value = state.sitePassword,
            onValueChange = state.onSitePasswordChange,
            label = "Senha"
        )

        CustomButton(
            text = "Salvar",
            onClick = {
                viewModel.editarLogin()
                onNavigateBack()
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun EditLoginScreenPreview() {
    EditLoginScreen(
        viewModel = EditLoginViewModel(
            fireStoreRepository = FireStoreRepository(
                fireStore = FirebaseFirestore.getInstance(),
            ),
            firebaseAuthRepository = FirebaseAuthRepository(
                firebaseAuth = FirebaseAuth.getInstance(),
                firebaseFirestore = FirebaseFirestore.getInstance()
            )
        ),
        onNavigateBack = {},
        state = EditLoginUiState()
    )
}
