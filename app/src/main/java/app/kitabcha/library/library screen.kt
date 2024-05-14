package app.kitabcha.library
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.library.libraryScreenViewModel
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.Content

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import  androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.UserEntity



@Composable fun LibraryScreen(navController: NavController,UserEnti: UserEntity) { // suspend problem  in repositories and as well as in the view model
    val viewModel = hiltViewModel<libraryScreenViewModel>()

    Content(viewModel , navController, UserEnti)
}
@Composable
fun Content(libraryViewModel: libraryScreenViewModel, navController: NavController, currentUserEntiity: UserEntity)
{

    //var allCategories = libraryViewModel.getCategoryIdUsingUserId(currentUserEntiity)
    val allCategores by libraryViewModel.CategoriesUser.collectAsStateWithLifecycle()
    val AllManga by libraryViewModel.AllManga.collectAsStateWithLifecycle()


     LazyColumn {
         itemsIndexed(
             listOf (allCategores)

         )
         {
             index,CategoryE ->
             Text(text = index.toString() + "    " +CategoryE[index].catTitle ,
                 modifier = Modifier  .clickable { // call to another screen getting a list<MangaEntity>
                  })


         }
     }







    var isPopupVisible by remember { mutableStateOf( false) }
    var text by remember { mutableStateOf("") }

    Column(

        modifier = Modifier
            .padding(40.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,


        )
    {
        
        Button(onClick = { isPopupVisible = true }

        ) {
            Text("Add category")

        }
    }
    if (isPopupVisible) {
        PopupTextField(
            text1 = text,
            onTextChanged = { text = it },
            onDismiss = { isPopupVisible = false },
            libraryViewModel = libraryViewModel,
            navController=  navController,
            currentUserEntiity = currentUserEntiity
        )
    }

}
@Composable
fun PopupTextField(
    text1: String,
    onTextChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    libraryViewModel: libraryScreenViewModel,
    navController: NavController,
    currentUserEntiity : UserEntity
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Category Name") },
        text = {
            TextField(
                value = text1,
                onValueChange = { onTextChanged(it) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(onClick = {
                if(text1.isNotEmpty()){
                libraryViewModel.insertCategory(text1,currentUserEntiity.id)
                text1.removeRange(0,text1.length)}

                onDismiss() }) {
                Text("ok")

            }
        }
    )
}


/*
*  if (isTextFieldVisible) {
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




                libraryViewModel.insertCategory(text,currentUserEntiity.id)//text as category name
            }
            isTextFieldVisible = !isTextFieldVisible
           }) {
            if (isTextFieldVisible) {
                Text("enter")
            }
            else {
                Text("+")
            }
        }
*
* */




