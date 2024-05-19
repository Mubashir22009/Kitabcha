package app.kitabcha.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import me.saket.telephoto.zoomable.coil.ZoomableAsyncImage
import me.saket.telephoto.zoomable.rememberZoomableImageState
import me.saket.telephoto.zoomable.rememberZoomableState

@Composable
fun Reader(
    chapterId: Int,
    sourceId: Long,
    readerViewModel: ReaderViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val readerPages by readerViewModel.readerPages.collectAsStateWithLifecycle()
    var pageNum by remember {
        mutableIntStateOf(0)
    }
    val zoomableState = rememberZoomableState()
    val scope = rememberCoroutineScope()
    val resetZoom: () -> Unit = { scope.launch { zoomableState.resetZoom(false) } }

    LaunchedEffect(Unit) {
        readerViewModel.loadSourcePages(chapterId, sourceId)
    }

    if (readerPages.isEmpty()) {
        LoadingCircle()
    } else {
        ZoomableAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model =
                ImageRequest.Builder(context)
                    .data(readerPages[pageNum])
                    .build(),
            state = rememberZoomableImageState(zoomableState),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            onClick = {
                if (pageNum + 1 != readerPages.size) {
                    pageNum++
                    resetZoom()
                }
            },
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "${pageNum + 1} / ${readerPages.size}",
                style =
                    TextStyle.Default.copy(
                        color = Color.Black,
                        drawStyle =
                            Stroke(
                                miter = 5f,
                                width = 1f,
                                join = StrokeJoin.Round,
                            ),
                    ),
            )
        }
    }
}

@Composable
fun LoadingCircle() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        LinearProgressIndicator(
            modifier =
                Modifier
                    .width(64.dp)
                    .fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}
