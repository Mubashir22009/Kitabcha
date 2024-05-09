package app.kitabcha.nav

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
import app.kitabcha.presentation.secondPage

@Composable
fun navCont()
{
  val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LoginScreen, builder = {
      composable (Routes.LoginScreen,)
      {
        LoginScreen(navController)
      }
      /*composable(Routes.secondScreen)
      {
            secondPage(navController)
      }*/

    } )

}
