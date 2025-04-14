package com.example.chavegen.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chavegen.R
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
    when {
        state.isLoading -> {
            LoadingView()
        }

        state.errorMessage != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material3.Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
                CustomButton(
                    text = "Voltar",
                    onClick = onNavigateBack
                )
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
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
            onTrailingIconClick = {state.onSiteNameChange("")},
            label = "Nome do Site",
            placeholder = "Edite a Nome do site",
            trailingIcon = Icons.Default.Clear,
            leadingIcon = ImageVector.vectorResource(R.drawable.icon_telefone)
        )
        CustomTextField(
            value = state.siteUrl,
            onValueChange = state.onSiteUrlChange,
            onTrailingIconClick = {state.onSiteUrlChange("")},
            label = "URL do Site",
            placeholder = "Edite a Url do site",
            trailingIcon = Icons.Default.Clear,
            leadingIcon = ImageVector.vectorResource(R.drawable.icon_link)
        )

        CustomTextField(
            value = state.siteUser,
            onValueChange = state.onSiteUserChange,
            onTrailingIconClick = {state.onSiteUserChange("")},
            label = "Usuário",
            leadingIcon = Icons.Default.Person,
            placeholder = "Edite o Usuario do site",
            trailingIcon = Icons.Default.Clear,
        )

        CustomTextField(
            value = state.sitePassword,
            onValueChange = state.onSitePasswordChange,
            onTrailingIconClick = {state.onSitePasswordChange("")},
            label = "Senha",
            leadingIcon = Icons.Default.Lock,
            isPasswordField = true,
            placeholder = "Edite sua Senha"
        )

        CustomButton(
            text = if (state.isLoading) "Salvando..." else "Salvar",
            onClick = {
                viewModel.editarLogin()
                onNavigateBack()
            },
            enabled = !state.isLoading
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
