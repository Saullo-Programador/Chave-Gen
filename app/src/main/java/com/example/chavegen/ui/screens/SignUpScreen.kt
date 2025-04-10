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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chavegen.ui.components.CustomButton
import com.example.chavegen.ui.components.CustomTextField
import com.example.chavegen.ui.state.SignUpUiState

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {

    SignUpContent(
        user = uiState.user,
        userOnValue = uiState.onUserChange,
        email = uiState.email,
        emailOnValue = uiState.onEmailChange,
        password = uiState.password,
        passwordOnValue = uiState.onPasswordChange,
        confirmPassword = uiState.confirmPassword,
        confirmPasswordOnValue = uiState.onConfirmPasswordChange,
        startOnClick = onSignUpClick,
        onSignInClick = onSignInClick,
        uiState = uiState.error
    )
}

@Composable
fun SignUpContent(
    modifier: Modifier = Modifier,
    user: String,
    userOnValue: (String) -> Unit,
    email: String,
    emailOnValue: (String) -> Unit,
    password: String,
    passwordOnValue: (String) -> Unit,
    confirmPassword: String,
    confirmPasswordOnValue: (String) -> Unit,
    startOnClick: () -> Unit,
    onSignInClick: () -> Unit,
    uiState: String?
) {
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        AnimatedVisibility(visible = uiState !=  null) {
            uiState?.let{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red)
                ) {
                    Text(
                        text = it,
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(23.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignUpForm(
                user = user,
                userOnValue = userOnValue,
                email = email,
                emailOnValue = emailOnValue,
                password = password,
                passwordOnValue = passwordOnValue,
                confirmPassword = confirmPassword,
                confirmPasswordOnValue = confirmPasswordOnValue,
                startOnClick = startOnClick,
                onSignInClick = onSignInClick
            )
        }
    }
}

@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
    user: String,
    userOnValue: (String) -> Unit,
    email: String,
    emailOnValue: (String) -> Unit,
    password: String,
    passwordOnValue: (String) -> Unit,
    confirmPassword: String,
    confirmPasswordOnValue: (String) -> Unit,
    startOnClick: () -> Unit,
    onSignInClick: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        CustomTextField(
            value = user,
            onValueChange = userOnValue,
            leadingIcon = Icons.Default.AccountCircle,
            trailingIcon = Icons.Default.Clear,
            label = "Usuario",
            placeholder = "Digite seu Nome"
        )
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
        CustomTextField(
            value = confirmPassword,
            onValueChange = confirmPasswordOnValue,
            leadingIcon = Icons.Default.Lock,
            isPasswordField = true,
            label = "Confirmar Senha",
            placeholder = "Digite sua Senha"
        )
        CustomButton(
            text = "Entrar",
            onClick = startOnClick,
            cornerRadius = 8,
            modifier = Modifier.padding(top = 5.dp)
        )
        TextButton(
            onClick = onSignInClick,
            Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)
        ) {
            Text(text = "Logar")
        }
    }
}

@Preview(showBackground = true, name = "Default")
@Composable
fun SignUpPreview() {
    SignUpScreen(
        onSignUpClick = {},
        onSignInClick = {},
        uiState = SignUpUiState()
    )
}

@Preview(showBackground = true, name = "With error")
@Composable
fun SignUp1Preview() {
    SignUpScreen(
        onSignUpClick = {},
        onSignInClick = {},
        uiState = SignUpUiState(
            error = "Error"
        )
    )
}