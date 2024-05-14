package app.kitabcha.presentation

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.kitabcha.source.AvailableSources


@Composable
fun SourceItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 14.sp,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    )
}


@Composable
fun SourceLazyColumn(
    
    modifier: Modifier = Modifier
) {
    val src = AvailableSources.sources.values.toList()
    Column(modifier = Modifier.padding(16.dp))
    {
        // TODO:
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = modifier
        ) {
            items(src.size) { index ->
                SourceItem(text = src[index].name)
            }
        }
    }



  
}
