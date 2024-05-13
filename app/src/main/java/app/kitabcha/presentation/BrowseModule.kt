package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.presentation.BrowseScreenViewModel

@Composable
fun BrowseScreenDriver(navController: NavController)
{
    var br_viewModel = hiltViewModel<BrowseScreenViewModel>()
    browseScreen(br_viewModel,navController= navController)
}

@Composable
fun browseScreen(BrowseViewModel: BrowseScreenViewModel, navController: NavController)
{
    val searchManga by BrowseViewModel.manga_searched.collectAsStateWithLifecycle()
    val searchingInProgress by BrowseViewModel.searchingInProgress.collectAsStateWithLifecycle()
    val mangasFoundBySearch by BrowseViewModel.mangasFoundBySearch.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        Row()
        {
            TextField(value = searchManga,
                onValueChange = BrowseViewModel::onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                placeholder = {Text(text="Search")},
                maxLines = 1)
//            Button(onClick = ( /*TODO*/ )) {
//                Text(text="search")
            }
        }


        Spacer(modifier=Modifier.height(16.dp))
        // Lazy column to show all results of search
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
        ) {


            }


        }
    }
}

