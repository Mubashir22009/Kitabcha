package app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import app.kitabcha.data.entity.MangaEntity
import app.kitabcha.data.repository.CategoryMangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LibraryMangaViewModel
    @Inject
    constructor(
        private val repository3: CategoryMangaRepository,
    ) : ViewModel() {
        private val _userCategoryManga = MutableStateFlow(emptyList<MangaEntity>())
        val userCategoryManga = _userCategoryManga.asStateFlow()

        suspend fun getMangaIdUsingCategoryId(id: Int) {
            withContext(IO) {
                val categoryManga = repository3.getAllMangasIDInCurrCategory(id)
                _userCategoryManga.tryEmit(categoryManga)
            }
        }
    }
