package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation



import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.R
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.navcont.Routes

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = hiltViewModel<LoginScreenViewModel>()
    Content(loginViewModel = viewModel , navController)
}

@Composable
fun Content(loginViewModel: LoginScreenViewModel , navController: NavController) {

    // this variable will determine the diplay of login screen or signup screen
    var doLogin by remember { mutableStateOf(true) }
    val showPassword by remember { mutableStateOf(value = false) }
    val localContext = LocalContext.current

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

        val x: String = if(doLogin) {
            "Enter Account info to Login"
        } else {
            "Enter Account info to Signup"
        }
        Text(
            text = x,
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp, start = 5.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        TextField(
            label= {Text("Enter UserName")},
            value = username,
            onValueChange = onUsernameChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(),
            maxLines = 1,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(30.dp)
        )
        TextField(
            label= {Text("Enter Password")},
            value = password,
            onValueChange = onPasswordChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(),
            maxLines = 1,
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(20.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row()
        {
            Button(onClick = {
                doLogin = !doLogin
            }) {
                if(doLogin) {
                    Text(text = stringResource(R.string.want_to_signup))
                }
                else {
                    Text(text = stringResource(R.string.want_to_login))
                }
            }

            Spacer(modifier= Modifier.padding(start=10.dp))

            Button(onClick = {
                if (doLogin) {
                    loginViewModel.loginUser(username, password) { user ->
                        if (user == null) {
                            Toast.makeText(localContext, "Unable to login", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(localContext, "Logged in :D", Toast.LENGTH_SHORT)
                                .show()

                             navController.navigate("${Routes.libraryScreen}/${user.id}")
                        }
                    }
                } else {
                    loginViewModel.insertUser(
                        UserEntity(
                            userName = username,
                            password = password
                        )
                    )
                }
            }) {
                if(doLogin) {
                    Text(text = stringResource(R.string.Login))
                }
                else {
                    Text(text = stringResource(R.string.Sign_Up))
                }
            }
        }
    }
}