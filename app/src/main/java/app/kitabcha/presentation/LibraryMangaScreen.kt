package app.kitabcha.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.navigation.Routes

@Composable
fun LibraryMangaScreen(
    navController: NavController,
    userId: Int,
    cateId: Int,
    libraryMangaViewModel: LibraryMangaViewModel = hiltViewModel(),
) {
    val allManga by libraryMangaViewModel.userCategoryManga.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        libraryMangaViewModel.getMangaIdUsingCategoryId(cateId)
    }

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
                    Text("Library")
                },
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = 15.dp),
                ) {
                    Button(
                        onClick = {
                            libraryMangaViewModel.delCategory(cateId)
                            navController.popBackStack()
                        },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Delete Category",
                        )
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
            if (allManga.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        allManga,
                    ) { manga ->
                        Row(
                            Modifier
                                .padding(15.dp)
                                .fillMaxSize(),
                        ) {
                            Text(
                                text = manga.mangaTitle,
                                modifier =
                                    Modifier
                                        .clickable {
                                            navController.navigate("${Routes.mangaScreen}/$userId/$cateId/${manga.mangaID}")
                                        }.fillMaxWidth(),
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
