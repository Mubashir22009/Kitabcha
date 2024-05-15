package app.kitabcha.presentation
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.runBlocking

@Composable fun mangaLibraryScreen(navController: NavController,UserId: Int,cateId:Int) { // suspend problem  in repositories and as well as in the view model
    val viewModel = hiltViewModel<libraryMangaViewModel>()

    Content1(viewModel , navController, UserId, cateId)
}

@Composable
fun Content1(mangalibraryViewModel: libraryMangaViewModel, navController: NavController, UserId: Int,cateId:Int ) {

    val AllManga by mangalibraryViewModel.AllManga.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        runBlocking {
            mangalibraryViewModel.getCategoryIdUsingUserId(UserId)
        }
    }



    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    )
    {

        runBlocking {
            mangalibraryViewModel.getMangaIdUsingCategoryId( cateId )
        }
        Text(text ="               " + "Manga Title "+"             "+"   Author    " ,
            modifier = Modifier
                .padding(45.dp)
                .size(30.dp)
               )

        if (AllManga.isNotEmpty()) {
            LazyColumn {

                itemsIndexed(
                    AllManga

                )
                { index, categoryMAngas ->

                    Text(text = (index+1).toString() + " - " + categoryMAngas.mangaTitle+"       "+categoryMAngas.mangaAuthor ,
                        modifier = Modifier
                            .padding(20.dp)
                            .size(15.dp)
                            .clickable { // call to another screen opening manga :  manga screen
                            })


                }
            }
        }
        else
        {
            Text(  modifier = Modifier.padding(30.dp).size(25.dp) , text ="empty " )
        }

    }


}
