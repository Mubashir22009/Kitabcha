package app.kitabcha.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.kitabcha.navigation.Routes
import app.kitabcha.source.AvailableSources

@Composable
fun SourceItem(
    text: String,
    sourceId: Long,
    userId: Int,
    navController: NavController,
) {
    Text(
        text = text,
        fontSize = 20.sp,
        modifier =
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable(onClick = {
                    navController.navigate("${Routes.browseScreen}/$userId/$sourceId")
                }),
    )
    Spacer(modifier = Modifier.height(4.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourceScreen(
    userId: Int,
    navController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors =
                    TopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                        actionIconContentColor = MaterialTheme.colorScheme.surfaceVariant,
                        navigationIconContentColor = MaterialTheme.colorScheme.secondary,
                        scrolledContainerColor = MaterialTheme.colorScheme.secondary,
                    ),
                title = {
                    Column {
                        Text(
                            text = "Select Source",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
            )
        },
    ) { pad ->
        val src = AvailableSources.sources.values.toList()
        Column(modifier = Modifier.padding(pad)) {
            LazyColumn(
                modifier = Modifier,
            ) {
                items(src.size) { index ->
                    SourceItem(text = src[index].name, src[index].id, userId, navController)
                }
            }
        }
    }
}
