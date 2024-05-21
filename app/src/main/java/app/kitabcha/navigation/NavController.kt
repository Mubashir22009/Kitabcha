package app.kitabcha.navcont

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.kitabcha.navigation.Routes
import app.kitabcha.presentation.CategoryScreen
import app.kitabcha.presentation.LibraryMangaScreen
import app.kitabcha.presentation.MangaScreen
import app.kitabcha.presentation.Reader
import app.kitabcha.presentation.SourceScreen
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.BrowseScreen
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.LoginScreen

@Composable
fun NavController() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.loginScreen, builder = {
        composable(Routes.loginScreen) {
            LoginScreen(navController)
        }
        composable(
            "${Routes.mangaScreen}/{userId}/{manga_id}",
            arguments =
                listOf(navArgument("userId") { type = NavType.IntType }, navArgument("manga_id") { type = NavType.IntType }),
        ) {
            val mangaId = it.arguments!!.getInt("manga_id")
            val userId = it.arguments!!.getInt("userId")
            MangaScreen(navController = navController, userId = userId, mangaId = mangaId)
        }
        composable(
            route = "${Routes.categoryScreen}/{id}",
            arguments =
                listOf(navArgument("id") { type = NavType.IntType }),
        ) {
            BackHandler(true) {
                Log.i("LOG_TAG", "Clicked back")
            }
            val userId = it.arguments!!.getInt("id")
            CategoryScreen(navController, userId)
        }

        composable(
            "${Routes.sourceScreen}/{id}",
            arguments =
                listOf(navArgument("id") { type = NavType.IntType }),
        ) {
            val userId = it.arguments!!.getInt("id")
            SourceScreen(userId, navController)
        }

        composable(
            "${Routes.mangaLibraryScreen}/{userId}/{Catid}",
            arguments =
                listOf(navArgument("userId") { type = NavType.IntType }, navArgument("Catid") { type = NavType.IntType }),
        ) {
            val categoryId = it.arguments!!.getInt("Catid")
            val userId = it.arguments!!.getInt("userId")
            LibraryMangaScreen(navController = navController, userId = userId, cateId = categoryId)
        }

        composable(
            "${Routes.browseScreen}/{id}/{sourceId}",
            arguments =
                listOf(navArgument("id") { type = NavType.IntType }, navArgument("sourceId") { type = NavType.LongType }),
        ) {
            val sourceId = it.arguments!!.getLong("sourceId")
            val userId = it.arguments!!.getInt("id")

            BrowseScreen(userId, sourceId)
        }

        composable(
            "${Routes.readerScreen}/{chapId}/{sourceId}",
            arguments =
                listOf(
                    navArgument("chapId") {
                        type = NavType.IntType
                    },
                    navArgument("sourceId") {
                        type = NavType.LongType
                    },
                ),
        ) {
            val sourceId = it.arguments!!.getLong("sourceId")
            val chapterId = it.arguments!!.getInt("chapId")

            Reader(chapterId = chapterId, sourceId = sourceId)
        }
    })
}
