package app.kitabcha.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.kitabcha.source.AvailableSources


@Composable
fun SourceItem(
    text: String,
    sourceId: Long,
    userId: Int,
    navController: NavController,
) {
    var context = LocalContext.current
    Text(
        text = text,
        fontSize = 25.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
            .clickable(onClick = {
                //TODO
                //navController.navigate()
                Toast.makeText(context,"oh yeah baby $sourceId", Toast.LENGTH_SHORT).show()
            })
    )
    Spacer(modifier = Modifier.height(4.dp))
}


@Composable
fun SourceScreen(
    userId: Int,
    navController: NavController
) {
    val src = AvailableSources.sources.values.toList()
    Column(modifier = Modifier)
    {
        Spacer(modifier = Modifier.height(14.dp))
        Text("Select Source", fontSize =  30.sp, fontWeight= FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier
        ) {
            items(src.size) { index ->
                SourceItem(text = src[index].name, src[index].id, userId, navController)
            }
        }
    }
}
