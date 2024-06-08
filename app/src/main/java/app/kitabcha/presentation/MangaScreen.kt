package app.kitabcha.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.navigation.Routes
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MangaScreen(
    navController: NavController,
    userId: Int,
    mangaId: Int,
) {
    val viewModel = hiltViewModel<MangaScreenViewModel>()

    MangaScreenContent(viewModel, navController, userId, mangaId)
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MangaScreenContent(
    mangaScreenViewModel: MangaScreenViewModel,
    navController: NavController,
    userId: Int,
    mangaId: Int,
) {
    val mangaChapters by mangaScreenViewModel.mangaChapters.collectAsStateWithLifecycle()
    val manga by mangaScreenViewModel.manga.collectAsStateWithLifecycle()
    val showResult: MutableStateFlow<Boolean> = MutableStateFlow(false)
    LaunchedEffect(key1 = Unit) {
        // true
        showResult.tryEmit(true)
        mangaScreenViewModel.getMangaFromDB(mangaId)
        if (mangaChapters.isEmpty()) {
            mangaScreenViewModel.getMangaFromSource(mangaId)
            mangaScreenViewModel.getMangaFromDB(mangaId)
        }
        // false
        showResult.tryEmit(false)
    }

    if (showResult.value) {
        com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.LoadingCircle()
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
                        Text("manga name") //manga!!.mangaTitle
                    },
                )
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
                                    "Chapter " + mangaChap.chapterNum
                                } else {
                                    "Chapter " + mangaChap.chapterNum.toInt()
                                }

                            Row(
                                Modifier
                                    .padding(15.dp)
                                    .fillMaxSize(),
                            ) {
                                Text(
                                    text = num.toString(),
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
