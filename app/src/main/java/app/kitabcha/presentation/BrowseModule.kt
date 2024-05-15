package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.MangaEntity
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.presentation.BrowseScreenViewModel
import app.kitabcha.source.AvailableSources
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun BrowseScreenDriver(navController: NavController,_UserID:Int,SourceID: Long)
{
    var br_viewModel = hiltViewModel<BrowseScreenViewModel>()
    browseScreen(br_viewModel,navController= navController,_UserID,SourceID)
}

@Composable
fun browseScreen(BrowseViewModel: BrowseScreenViewModel, navController: NavController,_UserID:Int,sourceID: Long)
{
     // TODO: This will come from Navigator
    val source = AvailableSources.sources[sourceID]!!

    val categoryList by BrowseViewModel.ListOfCategory.collectAsStateWithLifecycle()
    val searchManga by BrowseViewModel.manga_searched.collectAsStateWithLifecycle()
    //val searchingInProgress by BrowseViewModel.searchingInProgress.collectAsStateWithLifecycle()
    val mangaListToDisplay by BrowseViewModel.mangaList_toDisplay.collectAsStateWithLifecycle()
    val flag by BrowseViewModel.lazyListFlag.collectAsStateWithLifecycle()
    val pageNumber by BrowseViewModel._pagenumber.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {

        TextField(value = searchManga,
            onValueChange = BrowseViewModel::onSearchTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            placeholder = {Text(text="Search")},
            maxLines = 1)
        Spacer(modifier=Modifier.height(16.dp))
        Button(onClick = ( {BrowseViewModel.getMangas(sourceID,pageNumber)} )) // TODO: pass sourceID from navigator
        {
            Text(text = "search")
        }
        Spacer(modifier=Modifier.height(16.dp))
        // Lazy column to show all results of search
        if(flag)
        {
            LazyColumnOfList(Manga = mangaListToDisplay,BrowseViewModel,_UserID)
        }



    }
}


@Composable
fun LazyColumnOfList (Manga: List<MangaEntity>,BrowseViewModel : BrowseScreenViewModel,_UserID:Int) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        items(Manga.size) { index ->
            Text(
                text = Manga[index].mangaTitle,
                modifier = Modifier.clickable { BrowseViewModel.updateMangaEntity(Manga[index]) })
        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupTextField(
    text1: String, // It will have the text "Select Catagory"
    onConfirmation: () ->Unit,
    onDissmissRequest: () ->Unit, // dimiss when dismiss button is pressed
    browseViewModel: BrowseScreenViewModel,
    navController: NavController,
    CategoryList: List<CategoryEntity>
) {
    var SelectedOption  by remember { mutableStateOf(CategoryList[0]) }
    Dialog(
        onDismissRequest = { browseViewModel.makeMangaVarNull() },
        // radio button for each option
        content = {
            CategoryList.forEach { option ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        RadioButton(
                            selected = (option == SelectedOption),
                            onClick = { SelectedOption = option }
                        )
                        Text(text = option.catTitle, modifier = Modifier.padding(8.dp))
                    }
                )
            }
            // this is dismiss button
            TextButton(onClick = { browseViewModel.makeMangaVarNull() },
                modifier = Modifier.padding(8.dp)) {
                Text("Dismiss")
            }
            TextButton(onClick = { browseViewModel.makeMangaVarNull() },
                modifier = Modifier.padding(8.dp)) {
                Text("OK")
            }


        },
//        confirmButton = {
//            Button(onClick = {
//                if (text1.isNotEmpty()) {
//                    libraryViewModel.insertCategory(text1, UserId)
//                    text1.removeRange(0, text1.length)
//                }
//
//                onDismiss()
//            }) {
//                Text("ok")
//
//            }
    )



        }
    )
}





    @Composable
    fun MangaClicked(
        BrowseViewModel: BrowseScreenViewModel,
        manga: MangaEntity,
        UserID: Int,
        SourceID: Long
    ) {
        if (BrowseViewModel.currentManga != null) {

            BrowseViewModel.getCategory(UserID) // stores value in a variable in BrowserScreenViewModel named ListOfCategory

            // Getting the real MangaId generated by DB when we inserted the manga in our database
            val MangaIdFromDB = BrowseViewModel.getRealMangaID(manga.mangaURL, SourceID)
            PopupTextField()

        }

    }


