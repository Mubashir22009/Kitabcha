package app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.ChapterEntity
import app.kitabcha.data.repository.CategoryMangaRepository
import app.kitabcha.data.repository.CategoryRepository
import app.kitabcha.data.repository.ChapterRepository
import app.kitabcha.data.repository.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MangaViewModel
    @Inject
    constructor(
        private val repository: CategoryRepository,
        private val repository2: LibraryRepository,
        private val repository3: CategoryMangaRepository,
        private val repository4: ChapterRepository,
    ) : ViewModel() {
        fun delCategory(id: CategoryEntity) {
            viewModelScope.launch(IO) {
                repository.delete(id)
            }
        }

        private val MangaChapters = MutableStateFlow(emptyList<ChapterEntity>())
        val AllChapters = MangaChapters.asStateFlow()

        suspend fun getchaptersUsingmangaId(id: Int) {
            withContext(IO) {
                val Mangachps = repository4.getMangaChapters(id)
                MangaChapters.tryEmit(Mangachps)
            }
        }
    }
