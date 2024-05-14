package app.kitabcha.navcont


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.LoginScreen
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.library.LibraryScreen

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


        //mukees screen
        composable("${Routes.SourceScreen}/{id}" , arguments =
        listOf( navArgument("id"){type= NavType.IntType}))
        {
            var Id= it.arguments!!.getInt("id")
            sourceScreen(navController, Id)
        }



        //ahmad screen
        composable("${Routes.browseScreen}/{id}/{sourceId}", arguments =
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

 val sourceId:Int=0


 navController.navigate(route = "${Routes.browseScreen}/${id}/${sourceId}")

}


@Composable
fun BrowseScreen(navController: NavController, id:Int, sourceId:Int)
{

    navController.navigate(route = "${Routes.SourceScreen}/${id}")


}