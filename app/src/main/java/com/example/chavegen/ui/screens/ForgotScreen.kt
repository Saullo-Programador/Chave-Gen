package com.example.chavegen.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chavegen.R
import com.example.chavegen.ui.components.CustomButton
import com.example.chavegen.ui.components.CustomTextField
import com.example.chavegen.ui.components.ErrorMessage
import com.example.chavegen.ui.components.TopBarComponent
import com.example.chavegen.ui.state.SignInUiState

@Composable
fun ForgotScreen(
    uiState: SignInUiState,
    onForgotClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarComponent(
            title = "Esqueceu a senha?",
            fontSize = 20,
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = onBackClick
        )
        ErrorMessage(message = uiState.erro)

        ForgotContent(
            email = uiState.email,
            onEmailChange = uiState.onEmailChange,
            onForgotClick = onForgotClick
        )

    }

}

@Composable
fun ForgotContent(
    email: String,
    onEmailChange: (String) -> Unit,
    onForgotClick: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Esqueceu a senha?",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Digite seu e-mail para receber um link de recuperação, " +
                    "para poder auterar a senha da sua conta.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 25.dp, vertical = 7.dp)
        )

        CustomTextField(
            value = email,
            onValueChange = onEmailChange,
            onTrailingIconClick = { onEmailChange("") },
            leadingIcon = Icons.Default.Email,
            label = stringResource(id = R.string.email_Label),
            placeholder = stringResource(id = R.string.email_placeholder),
            trailingIcon = Icons.Default.Clear,
        )

        CustomButton(
            text = "Enviar",
            onClick = { onForgotClick() },
            cornerRadius = 8,
            modifier = Modifier.padding(top = 15.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotScreenPreview() {
    ForgotScreen(
        uiState = SignInUiState()
    )
}