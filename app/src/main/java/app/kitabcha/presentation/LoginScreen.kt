package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.nav.Routes

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = hiltViewModel<LoginScreenViewModel>()
    Content(loginViewModel = viewModel , navController)
}

@Composable
fun Content(loginViewModel: LoginScreenViewModel , navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        val username by loginViewModel.userName.collectAsStateWithLifecycle()
        val password by loginViewModel.userPassword.collectAsStateWithLifecycle()
        val onUsernameChange: (username: String) -> Unit = remember {
            return@remember loginViewModel::setUserName
        }
        val onPasswordChange: (password: String) -> Unit = remember {
            return@remember loginViewModel::setUserPassword
        }
        val onSubmit: (value: UserEntity) -> Unit = remember {
            return@remember loginViewModel::insertUser
        }
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            placeholder = {
                Text(text = "Username")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = {
                Text(text = "Password")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedButton(onClick = {
            onSubmit(
                UserEntity(
                    userName = username,
                    password = password
                )
            )
        }) {
            Text(text = "Register")
        }

    }
}