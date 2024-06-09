package app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import app.kitabcha.data.repository.ChapterRepository
import app.kitabcha.source.AvailableSources
import app.kitabcha.source.model.SChapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel
    @Inject
    constructor(
        private val chaptersRepository: ChapterRepository,
    ) : ViewModel() {
        private var _readerPages = MutableStateFlow(emptyList<String>())
        var readerPages = _readerPages.asStateFlow()

        private var _loadingError = MutableStateFlow("")
        var loadingError = _loadingError.asStateFlow()

        suspend fun loadSourcePages(
            chapterId: Int,
            sourceId: Long,
        ) {
            val source = AvailableSources.sources[sourceId]!!
            val dbChapter = chaptersRepository.getChapter(chapterId)
            val sChapter =
                SChapter(
                    url = dbChapter.chapterURL,
                    number = dbChapter.chapterNum,
                )
            val pages =
                try {
                    source.getPageList(sChapter).map {
                        source.getImageUrl(it)
                    }
                } catch (e: Exception) {
                    _loadingError.tryEmit("${e.javaClass.simpleName}: ${e.message}")
                    return
                }
            _readerPages.tryEmit(pages)
        }
    }
