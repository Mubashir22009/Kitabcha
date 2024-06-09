package app.kitabcha.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AssistChip
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.navigation.Routes
import app.kitabcha.presentation.MangaScreenViewModel.LoadingState
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
    val chaptersRead by mangaScreenViewModel.chaptersReadList.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = Unit) {
        mangaScreenViewModel.loading(LoadingState.FromDB)
        mangaScreenViewModel.getMangaFromDB(mangaId)
        mangaScreenViewModel.getAlreadyReadChapters(userId, mangaId)
        if (mangaChapters.isEmpty()) {
            mangaScreenViewModel.loading(LoadingState.FromSource)
            mangaScreenViewModel.getMangaFromSource(mangaId)
            mangaScreenViewModel.getMangaFromDB(mangaId)
        }
        mangaScreenViewModel.loading(LoadingState.Loaded)
    }

    if (loading == LoadingState.FromDB) {
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
                    modifier = Modifier.alpha(1f),
                )
            },
            bottomBar = {
                BottomAppBar(modifier = Modifier) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp),
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
            // +++++++++++++++++++++ modifier = Modifier.
        ) { pad ->
            val layoutDirection = LocalLayoutDirection.current
            LazyColumn(
                modifier =
                    Modifier
                        .padding(bottom = pad.calculateBottomPadding())
                        .padding(top = pad.calculateTopPadding())
                        .fillMaxSize(),
                state = listState,
//                contentPadding =
//                PaddingValues(
//                    start = pad.calculateStartPadding(layoutDirection),
//                    end = pad.calculateEndPadding(layoutDirection),
//                    bottom = pad.calculateBottomPadding(),
//                ),
            ) {
                item {
                    Box {
                        val backdropGradientColors =
                            listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background,
                            )
                        AsyncImage(
                            model =
                                ImageRequest.Builder(context)
                                    .data(manga?.cover)
                                    .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier =
                                Modifier
                                    .matchParentSize()
                                    .drawWithContent {
                                        drawContent()
                                        drawRect(
                                            brush = Brush.verticalGradient(colors = backdropGradientColors),
                                        )
                                    }
                                    .blur(4.dp)
                                    .alpha(0.2f),
                        )
                        Text(
                            text = manga?.mangaDesc ?: "",
                            modifier =
                                Modifier.padding(
                                    all = 4.dp,
                                ),
                        )
                    }
                }
                item {
                    val tags = manga?.mangaTag?.split(", ")
                    tags?.let {
                        LazyRow {
                            items(it) { tag ->
                                AssistChip(
                                    onClick = { /*TODO*/ },
                                    label = { Text(tag) },
                                )
                            }
                        }
                    }
                }
                if (loading == LoadingState.Loaded) {
                    if (mangaChapters.isNotEmpty()) {
                        items(
                            mangaChapters,
                        ) { mangaChap ->
                            // bool to check weather current chapter exists alreadyReadChapter List or not
                            val chapInList: Boolean = mangaChap.chapterID in chaptersRead
                            val num =
                                if (mangaChap.chapterNum.toInt()
                                        .toFloat() != mangaChap.chapterNum
                                ) {
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
                                                navController.navigate(
                                                    "${Routes.readerScreen}/${mangaChap.chapterID}/${manga!!.sourceID}",
                                                )
                                                scope.launch {
                                                    mangaScreenViewModel.insertChapterInAlreadyRead(
                                                        userId,
                                                        mangaId,
                                                        mangaChap.chapterID,
                                                    )
                                                }
                                            }
                                            .fillMaxWidth(),
                                    color = if (chapInList) Color.Magenta else Color.White,
                                )
                            }
                        }
                    }
                } else {
                    item {
                        LoadingCircle()
                    }
                }
            }
        }
    }
}
