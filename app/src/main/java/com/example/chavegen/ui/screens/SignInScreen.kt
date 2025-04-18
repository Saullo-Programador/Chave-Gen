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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.chavegen.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chavegen.ui.components.CustomButton
import com.example.chavegen.ui.components.CustomTextField
import com.example.chavegen.ui.components.ErrorMessage
import com.example.chavegen.ui.state.SignInUiState

@Composable
fun SignInScreen(
    uiState: SignInUiState,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    SignInContent(
        email = uiState.email,
        emailOnValue = uiState.onEmailChange,
        password = uiState.password,
        passwordOnValue = uiState.onPasswordChange,
        onSignInClick = onSignInClick,
        onSignUpClick = onSignUpClick,
        onForgotPasswordClick = onForgotPasswordClick,
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
    onForgotPasswordClick: () -> Unit = {},
    uiState: String?
) {
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ErrorMessage(
            message = uiState
        )
        Column(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 23.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SignInTop()
            SignInForm(
                email = email,
                emailOnValue = emailOnValue,
                password = password,
                passwordOnValue = passwordOnValue,
                onSignInClick = onSignInClick,
                onSignUpClick = onSignUpClick,
                onForgotPasswordClick = onForgotPasswordClick
            )
        }
    }
}

@Composable
fun SignInTop(){
    Column(
        modifier = Modifier
            .padding(end = 30.dp, start = 30.dp, bottom = 24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "Logo",
            modifier = Modifier.size(160.dp)
        )
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 50.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
        )
        Text(
            text = stringResource(id = R.string.menssage_login),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(horizontal = 50.dp)
        )
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
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
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
            label = stringResource(id = R.string.email_Label),
            placeholder = stringResource(id = R.string.email_placeholder)
        )
        CustomTextField(
            value = password,
            onValueChange = passwordOnValue,
            leadingIcon = Icons.Default.Lock,
            isPasswordField = true,
            label = stringResource(id = R.string.password_Label),
            placeholder = stringResource(id = R.string.password_placeholder)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(id = R.string.forgot_password),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable{onForgotPasswordClick()}
            )
        }
        CustomButton(
            text = stringResource(id = R.string.Logar),
            onClick = onSignInClick,
            cornerRadius = 8,
            modifier = Modifier.padding(top = 5.dp)
        )
        SignInBottom(
            onSignUpClick = onSignUpClick
        )
    }
}

@Composable
fun SignInBottom(
    onSignUpClick: () -> Unit
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
                text = "Não tem uma conta? "
            )
            Text(
                text = stringResource(id = R.string.sign_up),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable{onSignUpClick()}
            )
        }

    }
}

@Preview(showBackground = true, name = "default")
@Composable
fun SignIpPreview() {
    SignInScreen(
        onSignInClick = {},
        onSignUpClick = {},
        uiState = SignInUiState(),
        onForgotPasswordClick = {}
    )
}

@Preview(showBackground = true, name = "with error")
@Composable
fun SignIp1Preview() {
    SignInScreen(
        onSignInClick = {},
        onSignUpClick = {},
        onForgotPasswordClick = {},
        uiState = SignInUiState(
            erro = "Error ao fazer login"
        )
    )
}