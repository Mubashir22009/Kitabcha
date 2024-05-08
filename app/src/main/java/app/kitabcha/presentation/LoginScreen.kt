package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Visibility
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.kitabcha.R
import app.kitabcha.data.entity.UserEntity

@Composable
fun LoginScreen() {
    val viewModel = hiltViewModel<LoginScreenViewModel>()
    Content(loginViewModel = viewModel)
}

@Composable
fun Content(loginViewModel: LoginScreenViewModel) {

    // this variable will determine the diplay of login screen or signup screen
    var login_or_signup by remember { mutableStateOf(true) }
    var showPassword by remember { mutableStateOf(value = false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp),
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
        var x: String = ""
        if(login_or_signup)
        {
            x="Enter Account info to Login"
        }
        else
        {
            x= "Enter Account info to Sign Up"
        }
        Text(
            text = x,
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp,start = 5.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        TextField(
            label= {Text("Enter UserName")},
            value = username,
            onValueChange = onUsernameChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(),
            maxLines = 1,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally).padding(30.dp)
        )
//        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            label= {Text("Enter Password")},
            value = password,
            onValueChange = onPasswordChange,
//            placeholder = {
//                Text(text = "Password")
//            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(),
            maxLines = 1,
            visualTransformation = if (showPassword) {

                VisualTransformation.None

            } else {

                PasswordVisualTransformation()

            },
//            trailingIcon = {
//                if (showPassword) {
//                    IconButton(onClick = { showPassword = false }) {
//                        Icon(
//                            imageVector = Icons.Filled.Visibility,
//                            contentDescription = "hide_password"
//                        )
//                    }
//                } else {
//                    IconButton(
//                        onClick = { showPassword = true }) {
//                        Icon(
//                            imageVector = Icons.Filled.VisibilityOff,
//                            contentDescription = "hide_password"
//                        )
//                    }
//                }
//            }
//            ,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally).padding(20.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row()
        {
            Button(onClick = {
                login_or_signup = !login_or_signup
            }) {
                if(login_or_signup)
                {
                    Text(text = stringResource(R.string.want_to_signup))
                }
                else
                {
                    Text(text = stringResource(R.string.want_to_login))
                }

            }

            Spacer(modifier= Modifier.padding(start=10.dp))

            Button(onClick = {
                if(login_or_signup)
                {
                    onSubmit(
                        UserEntity(
                            userName = username,
                            password = password
                        )
                    )
                }
//                else
//                {
//
//                }

            }) {
                if(login_or_signup)
                {
                    Text(text = stringResource(R.string.Login))
                }
                else
                {
                    Text(text = stringResource(R.string.Sign_Up))
                }
            }
        }

    }
}