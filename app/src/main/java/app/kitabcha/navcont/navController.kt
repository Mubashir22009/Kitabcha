package app.kitabcha.navcont

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.presentation.LibraryScreen
import app.kitabcha.presentation.SourceScreen
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.LoginScreen

@Composable
fun navCont()
{
    val navController = rememberNavController()
    val a=UserEntity( userName = String() , password = String() )

    NavHost(navController = navController, startDestination = Routes.LoginScreen, builder = {
        composable(Routes.LoginScreen)
        {
            LoginScreen(navController)
        }

        composable(route=  "${Routes.libraryScreen}/{id}" , arguments =
        listOf( navArgument("id"){type= NavType.IntType}))
        {
            val _userId=it.arguments!!.getInt("id")
            LibraryScreen(navController, _userId)
        }

        composable("${Routes.SourceScreen}/{id}" , arguments =
        listOf( navArgument("id"){type= NavType.IntType}))
        {
            val _id = it.arguments!!.getInt("id")
            SourceScreen(_id, navController)
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
