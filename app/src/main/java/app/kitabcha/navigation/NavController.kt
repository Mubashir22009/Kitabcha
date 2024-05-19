package app.kitabcha.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.kitabcha.presentation.LibraryScreen
import app.kitabcha.presentation.MangaLibraryScreen
import app.kitabcha.presentation.SourceScreen
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.LoginScreen

@Composable
fun NavController() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.loginScreen, builder = {
        composable(Routes.loginScreen) {
            LoginScreen(navController)
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
            "${Routes.mangaLibraryScreen}/{userId}/{Catid}",
            arguments =
                listOf(navArgument("userId") { type = NavType.IntType }, navArgument("Catid") { type = NavType.IntType }),
        ) {
            val categoryId = it.arguments!!.getInt("Catid")
            val userId = it.arguments!!.getInt("userId")
            MangaLibraryScreen(navController = navController, UserId = userId, cateId = categoryId)
        }
//
//
//
//        //ahmad screen
//        composable("${Routes.browseScreen}/{id}/{sourceId}", arguments =
//        listOf( navArgument("id"){type= NavType.IntType} , navArgument("sourceId"){type= NavType.IntType}))
//        {
//            var sourceId= it.arguments!!.getInt("sourceId")
//            var Id = it.arguments!!.getInt("id")
//
//            BrowseScreen(navController, Id, sourceId)
//        }
    })
}
