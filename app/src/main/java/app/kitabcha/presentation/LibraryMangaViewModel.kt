package app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.MangaEntity
import app.kitabcha.data.repository.CategoryMangaRepository
import app.kitabcha.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LibraryMangaViewModel
    @Inject
    constructor(
        private val categoryMangaRepository: CategoryMangaRepository,
        private val categoryRepository: CategoryRepository,
    ) : ViewModel() {
        private val _userCategoryManga = MutableStateFlow(emptyList<MangaEntity>())
        val userCategoryManga = _userCategoryManga.asStateFlow()

        suspend fun getMangaIdUsingCategoryId(id: Int) {
            withContext(IO) {
                val categoryManga = categoryMangaRepository.getAllMangasIDInCurrCategory(id)
                _userCategoryManga.tryEmit(categoryManga)
            }
        }

        fun delCategory(categoryId: Int) {
            viewModelScope.launch {
                categoryRepository.delete(categoryId)
            }
        }
    }
