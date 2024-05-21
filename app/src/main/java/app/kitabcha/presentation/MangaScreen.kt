package app.kitabcha.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import kotlinx.coroutines.runBlocking

@Composable
fun MangaScreen(
    navController: NavController,
    UserId: Int,
    mangaId: Int,
) {
    val viewModel = hiltViewModel<MangaScreenViewModel>()

    Content2(viewModel, navController, UserId, mangaId)
}

@Composable
fun Content2(
    mangaScreenViewModel: MangaScreenViewModel,
    navController: NavController,
    UserId: Int,
    mangaId: Int,
) {
    val mangaChapters by mangaScreenViewModel.mangaChapters.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        runBlocking {
            mangaScreenViewModel.getchaptersUsingmangaId(mangaId)
        }
    }

    if (mangaChapters.isNotEmpty()) {
        LazyColumn(
            modifier =
                Modifier
                    .padding(top = 100.dp)
                    .padding(bottom = 30.dp),
        ) {
            itemsIndexed(
                mangaChapters,
            ) { _, mangaChap ->
                Text(
                    text = " -    chapter no." + mangaChap.chapterNum + "  owner manga id = " + mangaChap.ownerMangaID,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = (20.sp),
                    modifier =
                        Modifier
                            .padding(20.dp)
                            .clickable {
                                // call to another screen opening manga :  reader
                            },
                )
            }
        }
    } else {
        Text(text = " Empty ", modifier = Modifier.padding(top = 100.dp))
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
                text = "Button if needed",
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top,
        modifier =
            Modifier
                .fillMaxSize().padding(bottom = 15.dp),
    ) {
        Text(
            // text = "${manga.mangaTitle}        Author = ${manga.mangaAuthor}", after getting manga entity by manga id
            text = "info 888",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = (40.sp),
            modifier =
                Modifier
                    .padding(top = 20.dp),
            color = Color.Yellow,
        )
    }
}
