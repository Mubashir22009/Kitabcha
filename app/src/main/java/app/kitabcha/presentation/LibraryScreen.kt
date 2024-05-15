package app.kitabcha.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.navcont.Routes
import kotlinx.coroutines.runBlocking

@Composable fun LibraryScreen(navController: NavController,UserId: Int) { // suspend problem  in repositories and as well as in the view model
    val viewModel = hiltViewModel<LibraryScreenViewModel>()

    Content(viewModel , navController, UserId)
}

@Composable
fun Content(libraryViewModel: LibraryScreenViewModel, navController: NavController, UserId: Int) {

    //var allCategories = libraryViewModel.getCategoryIdUsingUserId(currentUserEntiity)
    val allCategores  by  libraryViewModel.CategoriesUser.collectAsStateWithLifecycle()
    val AllManga by libraryViewModel.AllManga.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        runBlocking {
            libraryViewModel.getCategoryIdUsingUserId(UserId)
        }
    }

    var isPopupVisible by remember { mutableStateOf( false) }
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
        )
    {
        Row( horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically ,
            modifier = Modifier
                .padding(20.dp)
                .align(alignment = Alignment.BottomEnd)
            ) {

            Button( modifier = Modifier
                .padding(10.dp)
                .align(alignment = Alignment.Top),
                onClick = { navController.navigate(route = "${Routes.SourceScreen}/${UserId}") }

            ) {

                Text("Source Screen")

            }
            Button(modifier = Modifier
                .padding(10.dp)
                .align(alignment = Alignment.CenterVertically),onClick = { isPopupVisible = true }

            ) {
                Text("Add category")

            }
        }
        Spacer(modifier = Modifier .padding(25.dp))

        runBlocking {
            libraryViewModel.getCategoryIdUsingUserId(UserId)
        }
        if (allCategores.isNotEmpty()) {
            LazyColumn {

                itemsIndexed(
                    allCategores

                )
                { index, categoryE ->
                    Text(text = (index+1).toString() + " - " + categoryE.catTitle,
                        modifier = Modifier
                            .padding(15.dp)
                            .clickable {  navController.navigate("${Routes.mangaLibraryScreen}/${UserId}/${categoryE.catID}")
                            })


                }
            }
        }
        else if(allCategores.isEmpty())
        {
            Text(  modifier = Modifier.padding(30.dp) , text ="empty " )
        }

    }
    if (isPopupVisible) {
        PopupTextField(
            text = text,
            onTextChanged = { text = it },
            onDismiss = { isPopupVisible = false },
            libraryViewModel = libraryViewModel,
            navController=  navController,
            UserId = UserId,
            clearText = { text = "" }
        )
    }

}

@Composable
fun PopupTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    libraryViewModel: LibraryScreenViewModel,
    navController: NavController,
    UserId: Int,
    clearText: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Category Name") },
        text = {
            TextField(
                value = text,
                onValueChange = { onTextChanged(it) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(onClick = {
                if(text.isNotEmpty()){


                libraryViewModel.insertCategory(text,UserId)
                clearText()}

                onDismiss() }) {
                Text("ok")
            }
        }
    )
}
