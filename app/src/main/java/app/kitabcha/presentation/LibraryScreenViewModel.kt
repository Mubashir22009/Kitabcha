package app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.MangaEntity
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.data.repository.CategoryMangaRepository
import app.kitabcha.data.repository.CategoryRepository
import app.kitabcha.data.repository.LibraryRepository
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LibraryScreenViewModel
    @Inject
    constructor(
        private val repository: CategoryRepository,
        private val repository2: LibraryRepository,
        private val repository3: CategoryMangaRepository,
        private val repositoryUser: UserRepository,
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

        fun delUser(id: UserEntity) {
            viewModelScope.launch(IO) {
                repositoryUser.delete(id)
            }
        }

        suspend fun getCategories(id: CategoryEntity) {
            return withContext(IO) {
                repository.getCategories(id.myLibrary)
            }
        }

        private val _userCategories = MutableStateFlow(emptyList<CategoryEntity>())
        val userCategories = _userCategories.asStateFlow()

        suspend fun getCategoryIdUsingUserId(id: Int) {
            withContext(IO) {
                val cats = repository2.getAllCategoriesOfUser(id)
                _userCategories.tryEmit(cats)
            }
        }

        private val _userCategoryManga = MutableStateFlow(emptyList<MangaEntity>())
        val userCategoryManga = _userCategoryManga.asStateFlow()

        suspend fun getMangaIdUsingCategoryId(id: CategoryEntity) {
            withContext(IO) {
                val categoryManga = repository3.getAllMangasIDInCurrCategory(id.catID)
                _userCategoryManga.tryEmit(categoryManga)
            }
        }

        private val ue = UserEntity(userName = "33", password = "11")
        private val uE = MutableStateFlow(ue)
        val userEnti = uE.asStateFlow()

        suspend fun getEntity(id: Int) {
            val userEnti = repositoryUser.getUserFromID(id)
            uE.tryEmit(userEnti)
        }

    /*fun loginUser(id_ : Int) {
        viewModelScope.launch {
            callback( repositoryUser.delete(id_) )
        }
    }*/
    }
