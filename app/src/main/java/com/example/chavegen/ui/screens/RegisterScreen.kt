package com.example.chavegen.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chavegen.ui.components.CustomButton
import com.example.chavegen.ui.components.CustomTextField
import com.example.chavegen.ui.components.DialogGerarSenha
import com.example.chavegen.ui.components.TopBarComponent
import com.example.chavegen.ui.state.RegisterUiState
import com.example.chavegen.ui.viewModel.RegisterViewModel

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    viewModel: RegisterViewModel
) {
    var openAlertDialog by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        RegisterContent(
            nameSite = uiState.nameSite,
            urlSite = uiState.urlSite,
            loginSite = uiState.loginSite,
            passwordSite = uiState.passwordSite,
            onNameSiteChange = viewModel::onNameSiteChange,
            onUrlSiteChange = viewModel::onUrlSiteChange,
            onLoginSiteChange = viewModel::onLoginSiteChange,
            onPasswordSiteChange = viewModel::onPasswordSiteChange,
            onGeneratePasswordClick = {openAlertDialog = true}
        )
    }
    if (openAlertDialog){
        DialogGerarSenha(
            closeAlertDialog = { openAlertDialog = false },
            onUsePassword = {senhaGerada ->
                viewModel.onPasswordSiteChange(senhaGerada)
            },
            viewModel = viewModel
        )
    }
}

@Composable
fun RegisterContent(
    nameSite: String,
    urlSite: String = "",
    loginSite: String = "",
    passwordSite: String = "",
    onNameSiteChange: (String) -> Unit,
    onUrlSiteChange: (String) -> Unit,
    onLoginSiteChange: (String) -> Unit,
    onPasswordSiteChange: (String) -> Unit,
    onGeneratePasswordClick: () -> Unit
){
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBarComponent(
            title = "Cadastro",
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            roundedCornerSize = 16
        )
        RegisterForm(
            nameSite = nameSite,
            urlSite = urlSite,
            loginSite = loginSite,
            passwordSite = passwordSite,
            onNameSiteChange = onNameSiteChange,
            onUrlSiteChange = onUrlSiteChange,
            onLoginSiteChange = onLoginSiteChange,
            onPasswordSiteChange = onPasswordSiteChange,
            onGeneratePasswordClick = onGeneratePasswordClick
        )
    }
}

@Composable
fun RegisterForm(
    nameSite: String,
    urlSite: String = "",
    loginSite: String = "",
    passwordSite: String = "",
    onNameSiteChange: (String) -> Unit,
    onUrlSiteChange: (String) -> Unit,
    onLoginSiteChange: (String) -> Unit,
    onPasswordSiteChange: (String) -> Unit,
    onGeneratePasswordClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (Modifier.fillMaxWidth()){
            Text(text = "Nome do Site:", fontSize = 17.sp, modifier =  Modifier.padding(start = 4.dp))
            CustomTextField(
                value = nameSite,
                onValueChange = onNameSiteChange,
                label = "Name",
                placeholder = "Digite o nome do site",
                trailingIcon = Icons.Default.Clear,
            )
        }
        Column (Modifier.fillMaxWidth()) {
            Text(
                text = "Url do Site:",
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
            CustomTextField(
                value = urlSite,
                onValueChange = onUrlSiteChange,
                label = "Url",
                placeholder = "Digite a Url do site",
                trailingIcon = Icons.Default.Clear,
            )
        }
        Column (Modifier.fillMaxWidth()) {
            Text(
                text = "Usuario do Site:",
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
            CustomTextField(
                value = loginSite,
                onValueChange = onLoginSiteChange,
                label = "User",
                placeholder = "Digite o Usuario do site",
                trailingIcon = Icons.Default.Clear,
                leadingIcon = Icons.Default.Person
            )
        }
        RegisterFormPassword(
            passwordSite = passwordSite,
            onPasswordSiteChange = onPasswordSiteChange,
            onGeneratePasswordClick = onGeneratePasswordClick
        )
    }
}

@Composable
fun RegisterFormPassword(
    passwordSite: String = "",
    onPasswordSiteChange: (String) -> Unit,
    onGeneratePasswordClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ){

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )


        Column(
            modifier = Modifier
                .fillMaxWidth() // Faz a coluna ocupar o espaço disponível
        ) {
            Text(
                text = "Senha do Site:",
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 4.dp)
            )

            CustomTextField(
                value = passwordSite,
                onValueChange = onPasswordSiteChange,
                label = "Password",
                placeholder = "Digite a senha do site",
                leadingIcon = Icons.Default.Lock,
                isPasswordField = true,
                trailingIcon = Icons.Default.Clear,
            )
        }
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            CustomButton(
                text = "Gerar Senha",
                onClick = onGeneratePasswordClick,
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview(){
    RegisterScreen(
        uiState = RegisterUiState(),
        viewModel = RegisterViewModel()
    )
}