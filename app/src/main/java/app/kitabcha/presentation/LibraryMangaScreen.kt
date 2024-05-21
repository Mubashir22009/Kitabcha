package app.kitabcha.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.navigation.Routes


@Composable fun MangaLibraryScreen(
    navController: NavController,
    UserId: Int,
    cateId: Int,
) {
    val viewModel = hiltViewModel<LibraryMangaViewModel>()

    Content1(viewModel, navController, UserId, cateId)
}

@Composable
fun Content1(
    mangalibraryMangaViewModel: LibraryMangaViewModel,
    navController: NavController,
    UserId: Int,
    cateId: Int,
) {
    val allManga by mangalibraryMangaViewModel.userCategoryManga.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        mangalibraryMangaViewModel.getMangaIdUsingCategoryId(cateId)
    }

    if (allManga.isNotEmpty()) {
        LazyColumn(
            modifier =
                Modifier
                    .padding(top = 30.dp)
                    .padding(bottom = 50.dp),
        ) {
            items(
                allManga,
            ) { manga ->

                Text(
                    text = manga.mangaTitle,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = (20.sp),
                    modifier =
                        Modifier
                            .padding(20.dp)
                            .clickable {
                                navController.navigate("${Routes.mangaScreen}/$UserId/${manga.mangaID}")
                            },
                )
            }
        }
    } else {
        Text(text = "Empty", modifier = Modifier.padding(20.dp))
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom,
        modifier =
            Modifier
                .fillMaxSize().padding(bottom = 15.dp),
    ) {
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge, // Adjust shape as needed
            colors = ButtonDefaults.buttonColors(Color.Yellow, contentColor = Color.Black),
        ) {
            Text(
                text = "Delete Category",
                // style = MaterialTheme.typography.button, // Adjust text style as needed
            )
        }
    }
}
