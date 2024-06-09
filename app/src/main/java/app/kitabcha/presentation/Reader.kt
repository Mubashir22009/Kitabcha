package app.kitabcha.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.Coil
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation.LoadingCircle
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
    var imageSize by remember { mutableStateOf(IntSize.Zero) }
    val zoomableState = rememberZoomableState()
    val scope = rememberCoroutineScope()
    val resetZoom: () -> Unit = { scope.launch { zoomableState.resetZoom(false) } }
    var imageLoading by remember {
        mutableStateOf(false)
    }
    val imageLoader = Coil.imageLoader(context)
    var enqueued by remember {
        mutableIntStateOf(-1)
    }
    var loadNextPlease by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {
        readerViewModel.loadSourcePages(chapterId, sourceId)
    }

    LaunchedEffect(loadNextPlease) {
        for (i in 1..3) {
            val next = pageNum + i
            if (next < readerPages.size && next > enqueued) {
                val request =
                    ImageRequest.Builder(context)
                        .data(readerPages[next])
                        .build()

                enqueued = next
                imageLoader.enqueue(request)
            }
        }
    }

    if (readerPages.isEmpty()) {
        LoadingCircle()
    } else {
        Box {
            ZoomableAsyncImage(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .onGloballyPositioned { coordinates ->
                            imageSize = coordinates.size
                        },
                model =
                    ImageRequest.Builder(context)
                        .data(readerPages[pageNum])
                        .listener(
                            remember {
                                object : ImageRequest.Listener {
                                    override fun onStart(request: ImageRequest) {
                                        imageLoading = true
                                        super.onStart(request)
                                    }

                                    override fun onSuccess(
                                        request: ImageRequest,
                                        result: SuccessResult,
                                    ) {
                                        imageLoading = false
                                        loadNextPlease++
                                        super.onSuccess(request, result)
                                    }

                                    override fun onError(
                                        request: ImageRequest,
                                        result: ErrorResult,
                                    ) {
                                        imageLoading = false
                                        super.onError(request, result)
                                    }
                                }
                            },
                        )
                        .build(),
                state = rememberZoomableImageState(zoomableState),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                onClick = { offset ->
                    val clickedSide = if (offset.x < imageSize.width / 2) "left" else "right"
                    if (clickedSide == "right" && pageNum + 1 < readerPages.size) {
                        pageNum++
                        resetZoom() // Reset zoom when navigating pages
                    } else if (clickedSide == "left" && pageNum - 1 >= 0) {
                        pageNum--
                        resetZoom()
                    }
                },
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "${pageNum + 1} / ${readerPages.size}",
                        style =
                            TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                drawStyle =
                                    Stroke(
                                        miter = 5f,
                                        width = 4f,
                                    ),
                            ),
                        modifier = Modifier.align(Alignment.Center),
                    )
                    Text(
                        text = "${pageNum + 1} / ${readerPages.size}",
                        style =
                            TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                            ),
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
            if (imageLoading) {
                LoadingCircle()
            }
        }
    }
}
