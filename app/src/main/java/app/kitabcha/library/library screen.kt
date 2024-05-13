package app.kitabcha.library

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.library.libraryScreenViewModel
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.Content


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = hiltViewModel<libraryScreenViewModel>()

    Content(viewModel , navController)
}
@Composable
fun Content(libraryViewModel: libraryScreenViewModel , navController: NavController)
{
    var isTextFieldVisible by remember  { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    //libraryViewModel.getCategories() maybe library id








        if (isTextFieldVisible) {
            BasicTextField(
                value = text,
                onValueChange = { newText -> text = newText },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        Button(onClick = {
            if(isTextFieldVisible) {

                libraryViewModel.insertCategory(text)//text as category name
            }
            isTextFieldVisible = !isTextFieldVisible
           }) {
            if (isTextFieldVisible) {
                Text("enter")
            } else {
                Text("+")
            }
        }
    }

}

