package app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.ChapterEntity
import app.kitabcha.data.entity.UserEntity
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
class MangaScreenViewModel
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

        private val _mangaChapters = MutableStateFlow(emptyList<ChapterEntity>())
        val mangaChapters = _mangaChapters.asStateFlow()

        suspend fun getchaptersUsingmangaId(id: Int) {
            withContext(IO) {
                val chapters = repository4.getMangaChapters(id)
                _mangaChapters.tryEmit(chapters)
            }
        }



    }
