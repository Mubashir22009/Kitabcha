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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
fun LazyColumnOfList (Manga: List<MangaEntity>,BrowseViewModel : BrowseScreenViewModel,_UserID:Int)
{

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        items(Manga.size) { index ->
        Text(text = Manga[index].mangaTitle, modifier = Modifier.clickable {BrowseViewModel.updateMangaEntity(Manga[index])})
    }
}

@Composable
fun MangaClicked(BrowseViewModel: BrowseScreenViewModel,manga: MangaEntity,UserID:Int)
{
    if(BrowseViewModel.ListOfCategory!=null)
    {

    }
    BrowseViewModel.getCategory(UserID) // stores value in a variable in BrowserScreenViewModel named ListOfCategory

    // TODO: Get MangaId ot Manga URl using the other (Mubashir you know about it)

}


@Composable
fun PopupTextField(
    text1: String,
    onTextChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    libraryViewModel: BrowseScreenViewModel,
    navController: NavController,
    UserId: Int
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
                    libraryViewModel.insertCategory(text1,UserId)
                    text1.removeRange(0,text1.length)}

                onDismiss() }) {
                Text("ok")

            }
        }
    )
}