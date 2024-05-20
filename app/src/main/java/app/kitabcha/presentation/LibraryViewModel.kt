package app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.MangaEntity
import app.kitabcha.data.repository.CategoryMangaRepository
import app.kitabcha.data.repository.CategoryRepository
import app.kitabcha.data.repository.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel
    @Inject
    constructor(
        private val repository: CategoryRepository,
        private val repository2: LibraryRepository,
        private val repository3: CategoryMangaRepository,
    ) : ViewModel() {
        fun insertCategory(
            id: String,
            userId: Int,
        ) {
            viewModelScope.launch(IO) {
                repository.insert(
                    CategoryEntity(
                        myLibrary = repository2.getLibID(userId),
                        catTitle = id,
                    ),
                )
            }
        }

        fun delCategory(id: CategoryEntity) {
            viewModelScope.launch(IO) {
                repository.delete(id)
            }
        }

        suspend fun getCategories(id: CategoryEntity) {
            return withContext(IO) {
                repository.getCategories(id.myLibrary)
            }
        }

        private val _userCategory = MutableStateFlow(emptyList<CategoryEntity>())
        val userCategory = _userCategory.asStateFlow()

        suspend fun getCategoryIdUsingUserId(id: Int) {
            withContext(IO) {
                val cats = repository2.getAllCategoriesOfUser(id)
                _userCategory.tryEmit(cats)
            }
        }

        private val _userCategoryManga = MutableStateFlow(emptyList<MangaEntity>())
        val userCategoryManga = _userCategoryManga.asStateFlow()

        suspend fun getMangaIdUsingCategoryId(id: Int) {
            withContext(IO) {
                val categoryManga = repository3.getAllMangasIDInCurrCategory(id)
                _userCategoryManga.tryEmit(categoryManga)
            }
        }

        private val ce = CategoryEntity(catTitle = "hdhhd", myLibrary = 69)
        private val CE = MutableStateFlow(ce)
        val CateEnti = CE.asStateFlow()

        suspend fun getOnlyCateEntity(id: Int) {
            val CateEnti = repository.getCategoryFromID(id)
            CE.tryEmit(CateEnti)
        }
    }
