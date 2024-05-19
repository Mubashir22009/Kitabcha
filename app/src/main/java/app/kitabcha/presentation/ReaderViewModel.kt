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
                source.getPageList(sChapter).map {
                    source.getImageUrl(it)
                }
            _readerPages.tryEmit(pages)
        }
    }
