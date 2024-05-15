package app.kitabcha.presentation
//package app.kitabcha.library

import app.kitabcha.data.repository.CategoryRepository
import app.kitabcha.data.repository.LibraryRepository



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.MangaEntity
import app.kitabcha.data.repository.CategoryMangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject



@HiltViewModel
class libraryScreenViewModel @Inject constructor(
    private val repository: CategoryRepository,
    private val repository2: LibraryRepository,
    private val repository3: CategoryMangaRepository,
) : ViewModel() {

    fun insertCategory(id: String, userId:Int) {


        viewModelScope.launch(IO) {
            repository.insert(
                CategoryEntity
                    (
                            myLibrary = repository2.getLibID(userId),
                             catTitle =id
                            )
            )
        }
    }
    fun delCategory(id: CategoryEntity) {
        viewModelScope.launch(IO) {
            repository.delete(id)
        }
    }
    suspend fun getCategories(id:CategoryEntity)
    {
        return withContext(IO)   {
            repository.getCategories(id.myLibrary)
        }
    }

    private val CategUser = MutableStateFlow(emptyList<CategoryEntity>())
    val CategoriesUser = CategUser.asStateFlow()
    suspend fun getCategoryIdUsingUserId(id: Int) {
       withContext(IO) {
           val cats = repository2.getAllCategoriesOfUser(id)
           CategUser.tryEmit(cats)
       }
    }
    private val MangaCategvar = MutableStateFlow(emptyList<MangaEntity>())
    val AllManga = MangaCategvar.asStateFlow()
    suspend fun getMangaIdUsingCategoryId(id: CategoryEntity) {
        withContext(IO) {
            val MangaOfCateg =  repository3.getAllMangasIDInCurrCategory(id.catID)
            MangaCategvar.tryEmit(MangaOfCateg)
        }
    }


}

