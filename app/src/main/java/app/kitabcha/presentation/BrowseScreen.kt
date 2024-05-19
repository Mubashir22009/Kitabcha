package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.presentation.BrowseScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseScreen(
    navController: NavController,
    userId: Int,
    sourceId: Long,
    browseScreenViewModel: BrowseScreenViewModel = hiltViewModel<BrowseScreenViewModel>(),
) {
    // val categoryList by browseScreenViewModel.categories.collectAsStateWithLifecycle()
    val searchManga by browseScreenViewModel.searchQuery.collectAsStateWithLifecycle()
    // val searchingInProgress by BrowseViewModel.searchingInProgress.collectAsStateWithLifecycle()
    val mangaListToDisplay by browseScreenViewModel.remoteManga.collectAsStateWithLifecycle()
    val flag by browseScreenViewModel.lazyListFlag.collectAsStateWithLifecycle()
    val pageNumber by browseScreenViewModel.page.collectAsStateWithLifecycle()
    val categories by browseScreenViewModel.categories.collectAsStateWithLifecycle()
    val selectedManga by browseScreenViewModel.currentManga.collectAsStateWithLifecycle()
    val selectedMangaId by browseScreenViewModel.currentMangaId.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        browseScreenViewModel.getCategory(userId)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Row(modifier = Modifier.padding(top = 15.dp)) {
            OutlinedTextField(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                },
                value = searchManga,
                label = { Text(text = "Search") },
                onValueChange = browseScreenViewModel::onSearchTextChange,
                modifier =
                    Modifier
                        .padding(top = 30.dp),
                maxLines = 1,
                visualTransformation = VisualTransformation.None,
                colors = TextFieldDefaults.colors(),
            )
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = (
                    {
                        browseScreenViewModel.getMangas(sourceId, pageNumber)
                    }
                ),
                modifier = Modifier.padding(top = 45.dp),
            ) {
                Text(text = "search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Lazy column to show all results of search
        if (flag) {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        // .verticalScroll(rememberScrollState())
                        .padding(16.dp),
            ) {
                items(mangaListToDisplay.size) { index ->
                    Text(
                        text = mangaListToDisplay[index].mangaTitle,
                        modifier =
                            Modifier.fillMaxWidth().clickable {
                                browseScreenViewModel.updateMangaEntity(mangaListToDisplay[index])
                            }.background(color = Color.DarkGray),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            if (selectedManga != null) {
                LaunchedEffect(Unit) {
                    browseScreenViewModel.getRealMangaID(selectedManga!!.mangaURL, sourceId)
                }
                if (selectedMangaId != null) {
                    var selectedCategory by remember { mutableStateOf(categories[0]) }
                    Dialog(
                        onDismissRequest = { browseScreenViewModel.makeMangaVarNull() },
                        // radio button for each option
                        content = {
                            Card {
                                categories.forEach { option ->
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            RadioButton(
                                                selected = (option == selectedCategory),
                                                onClick = { selectedCategory = option },
                                            )
                                            Text(text = option.catTitle, modifier = Modifier.padding(8.dp))
                                        },
                                    )
                                }
                                // this is dismiss button
                                TextButton(
                                    onClick = { browseScreenViewModel.makeMangaVarNull() },
                                    modifier = Modifier.padding(8.dp),
                                ) {
                                    Text("Dismiss")
                                }
                                TextButton(
                                    onClick = {
                                        scope.launch {
                                            browseScreenViewModel.pushMangaInCategory(
                                                selectedMangaId!!,
                                                selectedCategory.catID,
                                            )
                                        }
                                    },
                                    modifier = Modifier.padding(8.dp),
                                ) {
                                    Text("OK")
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}
