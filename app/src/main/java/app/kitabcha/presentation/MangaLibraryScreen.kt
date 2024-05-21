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
import app.kitabcha.navigation.Routes
import kotlinx.coroutines.runBlocking

@Composable fun MangaLibraryScreen(
    navController: NavController,
    UserId: Int,
    cateId: Int,
) {
    val viewModel = hiltViewModel<LibraryViewModel>()

    Content1(viewModel, navController, UserId, cateId)
}

@Composable
fun Content1(
    mangalibraryViewModel: LibraryViewModel,
    navController: NavController,
    UserId: Int,
    cateId: Int,
) {
    val allManga by mangalibraryViewModel.userCategoryManga.collectAsStateWithLifecycle()
    val mangaEnt by mangalibraryViewModel.cateEnti.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        runBlocking {
            mangalibraryViewModel.getCategoryIdUsingUserId(UserId)
        }
    }
    LaunchedEffect(key1 = Unit) {
        runBlocking {
            mangalibraryViewModel.getOnlyCateEntity(UserId)
        }
    }

    runBlocking {
        mangalibraryViewModel.getMangaIdUsingCategoryId(cateId)
    }

    if (allManga.isNotEmpty()) {
        LazyColumn(
            modifier =
                Modifier
                    .padding(top = 30.dp)
                    .padding(bottom = 50.dp),
        ) {
            itemsIndexed(
                allManga,
            ) { index, categoryMAngas ->

                Text(
                    text = (index + 1).toString() + " - " + categoryMAngas.mangaTitle + "       " + categoryMAngas.mangaAuthor,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = (20.sp),
                    modifier =
                        Modifier
                            .padding(20.dp)
                            .clickable {
                                navController.navigate("${ Routes.mangaScreen}/$UserId/${categoryMAngas.mangaID}")
                            },
                )
            }
        }
    } else {
        Text(text = " Empty ", modifier = Modifier.padding(20.dp))
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom,
        modifier =
            Modifier
                .fillMaxSize().padding(bottom = 15.dp),
    ) {
        Button(
            onClick = {
                mangalibraryViewModel.delCategory(mangaEnt)
                navController.navigate("${Routes.libraryScreen}/$UserId")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge, // Adjust shape as needed
            colors = ButtonDefaults.buttonColors(Color.Yellow, contentColor = Color.Black),
        ) {
            Text(
                text = "Delete Category" + mangaEnt.catTitle + mangaEnt.catID,
                // style = MaterialTheme.typography.button, // Adjust text style as needed
            )
        }
    }
}
