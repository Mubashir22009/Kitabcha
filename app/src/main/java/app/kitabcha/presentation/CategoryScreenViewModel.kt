package app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.CategoryEntity
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
class CategoryScreenViewModel
    @Inject
    constructor(
        private val repository: CategoryRepository,
        private val repository2: LibraryRepository,
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

                getCategoryIdUsingUserId(userId)
            }
        }

        fun delUser(userId: Int) {
            viewModelScope.launch(IO) {
                repositoryUser.delete(userId)
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
    }
