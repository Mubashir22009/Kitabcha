package app.kitabcha.library

import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.library.libraryScreenViewModel
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.Content


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = hiltViewModel<libraryScreenViewModel>()

    Content( libraryViewModel= viewModel , navController)
}

@Composable
fun

