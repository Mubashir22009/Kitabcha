package app.kitabcha.browseScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun BrowseScreenDriver(navController: NavController)
{
    var br_viewModel = hiltViewModel<BrowseScreenViewModel>()
    browseScreen(br_viewModel,navController= navController)
}

@Composable
fun browseScreen(BrowseViewModel:BrowseScreenViewModel,navController: NavController)
{
    Column()
    {


    }

}

