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
import app.kitabcha.presentation.LibraryScreen
import app.kitabcha.presentation.MangaLibraryScreen
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
            route = "${Routes.mangaScreen}/{userId}/{mangaid}",
            arguments =
                listOf(navArgument("userId") { type = NavType.IntType }, navArgument("mangaid") { type = NavType.IntType }),
        ) {
            val mangaId = it.arguments!!.getInt("mangaid")
            val userId = it.arguments!!.getInt("userId")
            MangaScreen(navController = navController, UserId = userId, mangaId = mangaId)
        }
        composable(
            route = "${Routes.libraryScreen}/{id}",
            arguments =
                listOf(navArgument("id") { type = NavType.IntType }),
        ) {
            BackHandler(true) {
                Log.i("LOG_TAG", "Clicked back")
            }
            val userId = it.arguments!!.getInt("id")
            LibraryScreen(navController, userId)
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
            "${Routes.mangaLibraryScreen}/{userId}/{cat_id}",
            arguments =
                listOf(navArgument("userId") { type = NavType.IntType }, navArgument("cat_id") { type = NavType.IntType }),
        ) {
            val categoryId = it.arguments!!.getInt("cat_id")
            val userIed = it.arguments!!.getInt("userId")
            MangaLibraryScreen(navController = navController, UserId = userIed, cateId = categoryId)
        }

        composable(
            "${Routes.browseScreen}/{id}/{sourceId}",
            arguments =
                listOf(navArgument("id") { type = NavType.IntType }, navArgument("sourceId") { type = NavType.LongType }),
        ) {
            val sourceId = it.arguments!!.getLong("sourceId")
            val userId = it.arguments!!.getInt("id")

            BrowseScreen(navController, userId, sourceId)
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
