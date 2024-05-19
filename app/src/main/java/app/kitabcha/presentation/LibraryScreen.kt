package app.kitabcha.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.room.util.TableInfo
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

    LaunchedEffect(key1 = Unit) {
        runBlocking {
            libraryViewModel.getCategoryIdUsingUserId(UserId)
        }
    }

    var isPopupVisible by remember { mutableStateOf( false) }
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(bottom=80.dp)
            .padding(top =30.dp)
            .fillMaxSize()
        )
    {

        //Spacer(modifier = Modifier .padding(25.dp))

        runBlocking {
            libraryViewModel.getCategoryIdUsingUserId(UserId)
        }
        if (allCategores.isNotEmpty()) {
            LazyColumn(modifier = Modifier .fillMaxSize()) {

                itemsIndexed(
                    allCategores

                )
                { index, categoryE ->
                    Row( Modifier.padding(15.dp).fillMaxSize()
                    ) {
                        Text(text = (index+1).toString() + " - " + categoryE.catTitle,
                            style = TextStyle(fontWeight = FontWeight.Bold) ,
                            fontSize = (20.sp),
                            modifier = Modifier
                                //.padding(bottom = (10).dp)
                                .clickable {
                                    navController.navigate("${Routes.mangaLibraryScreen}/${UserId}/${categoryE.catID}")

                                })
                    }



                }
            }
        }
        else
        {
            Text(  modifier = Modifier.padding(30.dp) , text ="empty " )
        }

    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom,

        modifier = Modifier
            .fillMaxSize().padding(bottom =15.dp)
    ) {

        Button(modifier = Modifier
            .align(alignment = Alignment.Bottom) ,
            onClick = { navController.navigate(route = "${Routes.SourceScreen}/${UserId}") }
                , shape = MaterialTheme.shapes.medium
                , colors = ButtonDefaults.buttonColors(Color.Yellow , contentColor = Color.Black   )
        ) {

            Text("Source Screen")

        }
        Button(
            onClick = { isPopupVisible = true },
            modifier = Modifier ,
            shape = MaterialTheme.shapes.large, // Adjust shape as needed
            colors = ButtonDefaults.buttonColors(Color.Yellow , contentColor = Color.Black   )


        ) {
            Text(
                text = "Add Category",
            )
        }
        Button(
            onClick = {  },
            modifier = Modifier ,
            shape = MaterialTheme.shapes.medium, // Adjust shape as needed
            colors = ButtonDefaults.buttonColors(Color.Yellow , contentColor = Color.Black   )


        ) {
            Text(
                text = "Delete User",
                //style = MaterialTheme.typography.button, // Adjust text style as needed
            )
        }


    }
    if (isPopupVisible) {
        PopupTextField(
            text = text,
            onTextChanged = { text = it },
            onDismiss = { isPopupVisible = false },
            libraryViewModel = libraryViewModel,
            navController=  navController,
            userId = UserId,
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
    userId: Int,
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


                libraryViewModel.insertCategory(text,userId)
                clearText()}

                onDismiss() }) {
                Text("ok")
            }
        }
    )
}
