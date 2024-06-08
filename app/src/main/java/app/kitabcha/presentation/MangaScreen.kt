package app.kitabcha.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun MangaScreen(
    navController: NavController,
    userId: Int,
    catId: Int,
    mangaId: Int,
) {
    val viewModel = hiltViewModel<MangaScreenViewModel>()

    MangaScreenContent(viewModel, navController, userId, catId, mangaId)
}

@Composable
fun MangaScreenContent(
    mangaScreenViewModel: MangaScreenViewModel,
    navController: NavController,
    userId: Int,
    catId: Int,
    mangaId: Int,
) {
    val mangaChapters by mangaScreenViewModel.mangaChapters.collectAsStateWithLifecycle()
    val manga by mangaScreenViewModel.manga.collectAsStateWithLifecycle()
    val loading by mangaScreenViewModel.loading.collectAsStateWithLifecycle()
    var scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        mangaScreenViewModel.loading(true)
        mangaScreenViewModel.getMangaFromDB(mangaId)
        if (mangaChapters.isEmpty()) {
            mangaScreenViewModel.getMangaFromSource(mangaId)
            mangaScreenViewModel.getMangaFromDB(mangaId)
        }
        mangaScreenViewModel.loading(false)
    }

    if (loading) {
        LoadingCircle()
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors =
                        TopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                            actionIconContentColor = MaterialTheme.colorScheme.surfaceVariant,
                            navigationIconContentColor = MaterialTheme.colorScheme.secondary,
                            scrolledContainerColor = MaterialTheme.colorScheme.secondary,
                        ),
                    title = {
                        Text(manga?.mangaTitle ?: "Unknown")
                    },
                )
            },
            bottomBar = {
                BottomAppBar(modifier = Modifier) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(start = 15.dp),
                    ) {
                        Button(
                            onClick = {
                                scope.launch {
                                    mangaScreenViewModel.deleteMangaFromCategory(catId, mangaId)
                                }
                                // here we will revert back to the previous screen
                                navController.popBackStack()
                            },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        ) {
                            Text(text = "Delete Manga")
                        }
                    }
                }
            },
        ) { pad ->
            Box(
                modifier =
                    Modifier
                        .padding(bottom = pad.calculateBottomPadding())
                        .padding(top = pad.calculateTopPadding())
                        .fillMaxSize(),
            ) {
                if (mangaChapters.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            mangaChapters,
                        ) { mangaChap ->
                            val num =
                                if (mangaChap.chapterNum.toInt().toFloat() != mangaChap.chapterNum) {
                                    mangaChap.chapterNum
                                } else {
                                    mangaChap.chapterNum.toInt()
                                }

                            Row(
                                Modifier
                                    .padding(15.dp)
                                    .fillMaxSize(),
                            ) {
                                Text(
                                    text = "Chapter $num",
                                    modifier =
                                        Modifier
                                            .clickable {
                                                navController.navigate("${Routes.readerScreen}/${mangaChap.chapterID}/${manga!!.sourceID}")
                                            }
                                            .fillMaxWidth(),
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Empty",
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }
    }
}
