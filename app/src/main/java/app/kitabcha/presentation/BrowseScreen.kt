package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.kitabcha.presentation.BrowseScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun BrowseScreen(
    userId: Int,
    sourceId: Long,
    browseScreenViewModel: BrowseScreenViewModel = hiltViewModel<BrowseScreenViewModel>(),
) {
    val searchManga by browseScreenViewModel.searchQuery.collectAsStateWithLifecycle()
    val mangaListToDisplay by browseScreenViewModel.remoteManga.collectAsStateWithLifecycle()
    val flag by browseScreenViewModel.lazyListFlag.collectAsStateWithLifecycle()
    val pageNumber by browseScreenViewModel.page.collectAsStateWithLifecycle()
    val categories by browseScreenViewModel.categories.collectAsStateWithLifecycle()
    val selectedManga by browseScreenViewModel.currentManga.collectAsStateWithLifecycle()
    val selectedMangaId by browseScreenViewModel.currentMangaId.collectAsStateWithLifecycle()
    val showLoadingCircle by browseScreenViewModel.showLoadingCircle.collectAsStateWithLifecycle()
    val localContext = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        browseScreenViewModel.getCategory(userId)
    }

    Column(
        modifier =
            Modifier
                .safeDrawingPadding()
                .fillMaxSize(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
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
                maxLines = 1,
                visualTransformation = VisualTransformation.None,
                colors = TextFieldDefaults.colors(),
                modifier = Modifier.weight(0.7f),
            )
            Button(
                onClick = (
                    {
                        browseScreenViewModel.newSearch(sourceId)
                    }
                ),
            ) {
                Text(text = "search")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            if (pageNumber > 1) {
                Button(onClick = { browseScreenViewModel.pageDown(sourceId) }) {
                    Text(text = "Previous Page")
                }
            }
            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = { browseScreenViewModel.pageUp(sourceId) }) {
                Text(text = "Next Page")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        // Lazy column to show all results of search
        if (showLoadingCircle) {
            LoadingCircle()
        } else {
            if (flag) {
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                ) {
                    items(mangaListToDisplay) { manga ->
                        Row(
                            modifier =
                                Modifier
                                    .padding(8.dp)
                                    .fillMaxSize(),
                        ) {
                            Text(
                                text = manga.mangaTitle,
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            browseScreenViewModel.updateMangaEntity(manga)
                                        },
                            )
                        }
                    }
                }
                if (selectedManga != null) {
                    LaunchedEffect(Unit) {
                        browseScreenViewModel.getRealMangaID(selectedManga!!.mangaURL, sourceId)
                    }
                    if (selectedMangaId != null) {
                        if (categories.isNotEmpty()) {
                            var selectedCategory by remember { mutableStateOf(categories[0]) }

                            Dialog(
                                onDismissRequest = { browseScreenViewModel.makeMangaVarNull() },
                                // radio button for each option
                                content = {
                                    Column {
                                        Card {
                                            categories.forEach { option ->
                                                Row(
                                                    Modifier
                                                        .fillMaxWidth()
                                                        .padding(8.dp),
                                                    content = {
                                                        RadioButton(
                                                            selected = (option == selectedCategory),
                                                            onClick = { selectedCategory = option },
                                                        )
                                                        Text(
                                                            text = option.catTitle,
                                                            modifier = Modifier.padding(8.dp),
                                                        )
                                                    },
                                                )
                                            }
                                            Row {
                                                TextButton(
                                                    onClick = { browseScreenViewModel.makeMangaVarNull() },
                                                    modifier = Modifier.padding(8.dp),
                                                ) {
                                                    Text("Dismiss")
                                                }
                                                Spacer(modifier = Modifier.width(16.dp))
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
                                        }
                                    }
                                },
                            )
                        } else {
                            Toast.makeText(
                                localContext,
                                "Category List is Empty, 1st make a category",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingCircle() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        LinearProgressIndicator(
            modifier =
                Modifier
                    .width(64.dp)
                    .fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}
