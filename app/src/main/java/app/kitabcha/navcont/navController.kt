package app.kitabcha.navcont

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.kitabcha.ui.theme.KitabchaTheme
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.library.LibraryScreen
import dagger.hilt.internal.ComponentEntryPoint

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

        composable(route=  "libraryScreen/{id}" , arguments =
        listOf( navArgument("id"){type= NavType.IntType}))
        {
            val _userId=it.arguments!!.getInt("id")
            LibraryScreen(navController, _userId)
        }


        //mukees screen
        composable("SourceScreen/{id}" , arguments =
        listOf( navArgument("id"){type= NavType.IntType}))
        {
            var Id= it.arguments!!.getInt("id")
            sourceScreen(navController, Id)
        }



        //ahmad screen
        composable("browseScreen/{id}/{sourceId}", arguments =
        listOf( navArgument("id"){type= NavType.IntType} , navArgument("sourceId"){type= NavType.IntType}))
        {
            var sourceId= it.arguments!!.getInt("sourceId")
            var Id = it.arguments!!.getInt("id")

            BrowseScreen(navController, Id, sourceId)
        }




    })

}

@Composable
fun sourceScreen(navController: NavController, id:Int)
{



}


@Composable
fun BrowseScreen(navController: NavController, id:Int, sourceId:Int)
{



}