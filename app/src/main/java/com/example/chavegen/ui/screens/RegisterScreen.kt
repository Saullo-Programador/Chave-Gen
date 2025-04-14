package com.example.chavegen.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chavegen.R
import com.example.chavegen.authentication.FirebaseAuthRepository
import com.example.chavegen.repository.FireStoreRepository
import com.example.chavegen.ui.components.CustomButton
import com.example.chavegen.ui.components.CustomTextField
import com.example.chavegen.ui.components.DialogGerarSenha
import com.example.chavegen.ui.components.TopBarComponent
import com.example.chavegen.ui.state.RegisterUiState
import com.example.chavegen.ui.viewModel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterMain(
    uiState: RegisterUiState,
    viewModel: RegisterViewModel,
    onSaveSuccess: () -> Unit,
    onBackClick: () -> Unit = {}
) {
    RegisterScreen(
        uiState = uiState,
        viewModel = viewModel,
        onSaveSuccess = onSaveSuccess,
        onBackClick = onBackClick
    )
}

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    viewModel: RegisterViewModel,
    onSaveSuccess: () -> Unit,
    onBackClick: () -> Unit = {}
) {
    var openAlertDialog by remember {
        mutableStateOf(false)
    }
    val eventMessage by viewModel.eventMessage.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(eventMessage) {
        eventMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            if (message == "Sucesso ao salvar o login") {
                onSaveSuccess()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(insets = WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarComponent(
            title = "Cadastro",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = onBackClick
        )
        RegisterContent(
            siteName = uiState.siteName,
            siteUrl = uiState.siteUrl,
            siteUser = uiState.siteUser,
            sitePassword = uiState.sitePassword,
            onSiteNameChange = uiState.onSiteNameChange,
            onSiteUrlChange = uiState.onSiteUrlChange,
            onSiteUserChange = uiState.onSiteUserChange,
            onSitePasswordChange = uiState.onSitePasswordChange,
            onGeneratePasswordClick = { openAlertDialog = true },
            salvarLogin = {
                scope.launch{
                    val loginValido = withContext(Dispatchers.IO) { viewModel.verificarLogin() }
                    withContext(Dispatchers.Main) {
                        if (loginValido) {
                            onSaveSuccess()
                        }
                    }
                }
            }
        )
    }
    if (openAlertDialog){
        DialogGerarSenha(
            closeAlertDialog = { openAlertDialog = false },
            onUsePassword = {senhaGerada ->
                uiState.onSitePasswordChange(senhaGerada)
            },
            viewModel = viewModel
        )
    }
}

@Composable
fun RegisterContent(
    siteName: String = "",
    siteUrl: String = "",
    siteUser: String = "",
    sitePassword: String = "",
    onSiteNameChange: (String) -> Unit = {},
    onSiteUrlChange: (String) -> Unit = {},
    onSiteUserChange: (String) -> Unit = {},
    onSitePasswordChange: (String) -> Unit = {},
    onGeneratePasswordClick: () -> Unit,
    salvarLogin: () -> Unit
){
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //RegisterIconItem()
            RegisterForm(
                siteName = siteName,
                siteUrl = siteUrl,
                siteUser = siteUser,
                sitePassword = sitePassword,
                onSiteNameChange = onSiteNameChange,
                onSiteUrlChange = onSiteUrlChange,
                onSiteUserChange = onSiteUserChange,
                onSitePasswordChange = onSitePasswordChange,
                onGeneratePasswordClick = onGeneratePasswordClick
            )
            CustomButton(
                text = "Salvar Login",
                fontSize = 19,
                icon = ImageVector.vectorResource(id = R.drawable.icon_save),
                onClick = { salvarLogin()},
            )
        }

    }
}
/*
@Composable
fun RegisterIconItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(80.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "add icon",
                modifier = Modifier.size(70.dp)
            )
        }
        Text("Adicionar Icon", fontSize = 20.sp)
    }
}
*/
@Composable
fun RegisterForm(
    siteName: String = "",
    siteUrl: String = "",
    siteUser: String = "",
    sitePassword: String = "",
    onSiteNameChange: (String) -> Unit = {},
    onSiteUrlChange: (String) -> Unit = {},
    onSiteUserChange: (String) -> Unit = {},
    onSitePasswordChange: (String) -> Unit = {},
    onGeneratePasswordClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (Modifier.fillMaxWidth()){
            Text(text = "Nome do Site:", fontSize = 17.sp, modifier =  Modifier.padding(start = 4.dp))
            CustomTextField(
                value = siteName,
                onValueChange = onSiteNameChange,
                onTrailingIconClick = {onSiteNameChange("")},
                label = "Name",
                placeholder = "Digite o nome do site",
                trailingIcon = Icons.Default.Clear,
                leadingIcon = ImageVector.vectorResource(R.drawable.icon_telefone)
            )
        }
        Column (Modifier.fillMaxWidth()) {
            Text(
                text = "Url do Site:",
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
            CustomTextField(
                value = siteUrl,
                onValueChange = onSiteUrlChange,
                onTrailingIconClick = {onSiteUrlChange("")},
                label = "Url",
                placeholder = "Digite a Url do site",
                trailingIcon = Icons.Default.Clear,
                leadingIcon = ImageVector.vectorResource(R.drawable.icon_link)
            )
        }
        Column (Modifier.fillMaxWidth()) {
            Text(
                text = "Usuario do Site:",
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
            CustomTextField(
                value = siteUser,
                onValueChange = onSiteUserChange,
                onTrailingIconClick = {onSiteUserChange("")},
                label = "User",
                placeholder = "Digite o Usuario do site",
                trailingIcon = Icons.Default.Clear,
                leadingIcon = Icons.Default.Person
            )
        }
        RegisterFormPassword(
            sitePassword = sitePassword,
            onSitePasswordChange = onSitePasswordChange,
            onGeneratePasswordClick = onGeneratePasswordClick
        )
    }
}

@Composable
fun RegisterFormPassword(
    sitePassword: String = "",
    onSitePasswordChange: (String) -> Unit,
    onGeneratePasswordClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ){

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Senha do Site:",
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 4.dp)
            )

            CustomTextField(
                value = sitePassword,
                onValueChange = onSitePasswordChange,
                onTrailingIconClick = {onSitePasswordChange("")},
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

    val viewModel = RegisterViewModel(
        fireStoreRepository = FireStoreRepository(
            fireStore = FirebaseFirestore.getInstance(),
        ),
        fireAuth = FirebaseAuthRepository(
            firebaseAuth = FirebaseAuth.getInstance(),
            firebaseFirestore = FirebaseFirestore.getInstance()
        )
    )
    RegisterScreen(
        uiState = RegisterUiState(),
        viewModel = viewModel,
        onSaveSuccess = {},
    )
}