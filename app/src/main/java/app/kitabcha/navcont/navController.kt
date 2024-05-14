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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.kitabcha.ui.theme.KitabchaTheme
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.composable
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.library.LibraryScreen

@Composable
fun navCont()
{
    val navController = rememberNavController()
    val a=UserEntity( userName = String() , password = String() )
    val dummyId:Int = 200
    val dummySourceId:Int = 200

    NavHost(navController = navController, startDestination = Routes.LoginScreen, builder = {
        composable(Routes.LoginScreen)
        {
            LoginScreen(navController)
        }
        composable(Routes.libraryScreen+"/{n}")
        {
            LibraryScreen(navController, dummyId)
        }


       /* mukees screen
        composable(Routes.SourceScreen)
        {
            SourceScreen(navController, dummyId)
        }
        */

        /*
        ahmad screen
        composable(Routes.browseScreen)
        {
            BrowseScreen(navController, dummyId, dummySourceId)
        }
        * */



    })

}