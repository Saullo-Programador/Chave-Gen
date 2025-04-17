package com.example.chavegen.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chavegen.R
import com.example.chavegen.ui.components.CustomButton
import com.example.chavegen.ui.components.CustomTextField
import com.example.chavegen.ui.components.ErrorMessage
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
        ErrorMessage(message = uiState)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 23.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignUpTop()
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
fun SignUpTop(){
    Row (
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "Logo",
            modifier = Modifier.size(140.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 50.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
            )
            Text(
                text = stringResource(id = R.string.menssage_sign_up),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
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
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        CustomTextField(
            value = user,
            onValueChange = userOnValue,
            leadingIcon = Icons.Default.Person,
            trailingIcon = Icons.Default.Clear,
            label = "Usuario",
            placeholder = "Digite seu Nome"
        )
        CustomTextField(
            value = email,
            onValueChange = emailOnValue,
            leadingIcon = Icons.Default.Email,
            trailingIcon = Icons.Default.Clear,
            label = stringResource(R.string.email_Label),
            placeholder = stringResource(R.string.email_placeholder)
        )
        CustomTextField(
            value = password,
            onValueChange = passwordOnValue,
            leadingIcon = Icons.Default.Lock,
            isPasswordField = true,
            label = stringResource(R.string.password_Label),
            placeholder = stringResource(R.string.password_placeholder)
        )
        CustomTextField(
            value = confirmPassword,
            onValueChange = confirmPasswordOnValue,
            leadingIcon = Icons.Default.Lock,
            isPasswordField = true,
            label = stringResource(R.string.confirm_password_Label),
            placeholder = stringResource(R.string.confirm_password_placeholder)
        )
        CustomButton(
            text = stringResource(R.string.cadastrar),
            onClick = startOnClick,
            cornerRadius = 8,
            modifier = Modifier.padding(top = 5.dp)
        )
        SignUpBottom (
            onSignInClick = onSignInClick
        )
    }
}


@Composable
fun SignUpBottom(
    onSignInClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .padding(horizontal = 6.dp)
                    .background(MaterialTheme.colorScheme.onBackground)
            )
            Text(
                text = "Ou",
                modifier = Modifier
                    .padding(10.dp)
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .padding(horizontal = 6.dp)
                    .background(MaterialTheme.colorScheme.onBackground)
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Já tem uma conta? "
            )
            Text(
                text = stringResource(id = R.string.sign_in),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable{onSignInClick()}
            )
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