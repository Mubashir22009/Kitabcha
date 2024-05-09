package app.kitabcha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import app.kitabcha.ui.theme.KitabchaTheme
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import app.kitabcha.nav.navCont
import app.kitabcha.nav.Routes
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navCont()
            /* KitabchaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }*/


        }
    }
}

