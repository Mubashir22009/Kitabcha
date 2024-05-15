package app.kitabcha.presentation
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.runBlocking

@Composable fun mangaLibraryScreen(navController: NavController,UserId: Int,cateId:Int) {
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


    runBlocking {
        mangalibraryViewModel.getMangaIdUsingCategoryId(cateId)
    }


    if (AllManga.isNotEmpty()) {
        LazyColumn {

            itemsIndexed(
                AllManga

            )
            { index, categoryMAngas ->

                Text(text = (index + 1).toString() + " - " + categoryMAngas.mangaTitle + "       " + categoryMAngas.mangaAuthor,
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable { // call to another screen opening manga :  manga screen
                        })


            }
        }
    }
    else
    {
        Text(text = " Empty " , modifier =Modifier.padding(20.dp) )
        
    }
}
