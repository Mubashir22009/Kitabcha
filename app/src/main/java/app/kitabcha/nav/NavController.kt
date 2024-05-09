package app.kitabcha.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.LoginScreen

@Composable
fun NavCont()
{
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LoginScreen, builder = {
      composable(Routes.LoginScreen)
      {
        LoginScreen(navController)
      }
      /*composable(Routes.secondScreen)
      {
            secondPage(navController)
      }*/

    } )

}
