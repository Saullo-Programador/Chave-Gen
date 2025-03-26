package com.example.chavegen.ui.screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chavegen.ui.components.CustomButton
import com.example.chavegen.ui.components.CustomTextField
import com.example.chavegen.ui.state.SignInUiState

@Composable
fun SignInScreen(
    uiState: SignInUiState,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    SignInContent(
        email = uiState.email,
        emailOnValue = uiState.onEmailChange,
        password = uiState.password,
        passwordOnValue = uiState.onPasswordChange,
        onSignInClick = onSignInClick,
        onSignUpClick = onSignUpClick,
        uiState = uiState.erro
    )
}

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    email: String,
    emailOnValue: (String) -> Unit,
    password: String,
    passwordOnValue: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    uiState: String?
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val isErro = uiState != null
        AnimatedVisibility(visible = isErro) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
            ) {
                val error = uiState ?: ""
                Text(
                    text = error,
                    Modifier
                        .padding(16.dp),
                    color = Color.White
                )
            }
        }
        Column(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(23.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInForm(
                email = email,
                emailOnValue = emailOnValue,
                password = password,
                passwordOnValue = passwordOnValue,
                onSignInClick = onSignInClick,
                onSignUpClick = onSignUpClick
            )
        }
    }
}

@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    email: String,
    emailOnValue: (String) -> Unit,
    password: String,
    passwordOnValue: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        CustomTextField(
            value = email,
            onValueChange = emailOnValue,
            leadingIcon = Icons.Default.Email,
            trailingIcon = Icons.Default.Clear,
            label = "E-mail",
            placeholder = "Digite seu Email"
        )
        CustomTextField(
            value = password,
            onValueChange = passwordOnValue,
            leadingIcon = Icons.Default.Lock,
            isPasswordField = true,
            label = "Senha",
            placeholder = "Digite sua Senha"
        )
        CustomButton(
            text = "Entrar",
            onClick = onSignInClick,
            cornerRadius = 8,
            modifier = Modifier.padding(top = 5.dp)
        )
        TextButton(
            onClick = onSignUpClick,
            Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)
        ) {
            Text(text = "Cadastrar")
        }
    }
}

@Preview(showBackground = true, name = "default")
@Composable
fun SignIpPreview() {
    SignInScreen(
        onSignInClick = {},
        onSignUpClick = {},
        uiState = SignInUiState()
    )
}

@Preview(showBackground = true, name = "with error")
@Composable
fun SignIp1Preview() {
    SignInScreen(
        onSignInClick = {},
        onSignUpClick = {},
        uiState = SignInUiState(
            erro = "Error ao fazer login"
        )
    )
}