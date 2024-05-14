package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.library
//package app.kitabcha.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.kitabcha.data.repository.CategoryRepository
import app.kitabcha.data.repository.LibraryRepository



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.room.ColumnInfo
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.MangaEntity
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.data.repository.CategoryMangaRepository
import app.kitabcha.data.repository.MangaRepository
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
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
    suspend fun getCategoryIdUsingUserId(id: UserEntity) {
       withContext(IO) {
           val cats = repository2.getAllCategoriesOfUser(id.id)
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

