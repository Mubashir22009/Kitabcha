package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.library
//package app.kitabcha.library

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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject



@HiltViewModel
class libraryScreenViewModel @Inject constructor(
    UserEnti:UserEntity ,

    private val repository: CategoryRepository,
    private val repository2: LibraryRepository,
    private val repository3: CategoryMangaRepository,
    private val repository4: UserRepository

    , navController: NavController
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
            repository.getCategories(id)
        }
    }
     suspend fun getCategoryIdUsingUserId(id: UserEntity): Flow<List<CategoryEntity>>
    {
        return withContext(IO)   {
            repository2.getAllCategoriesOfUser(id.id)
        }
    }
    suspend fun getMangaIdUsingCategoryId(id: CategoryEntity): Flow<List<MangaEntity>>
    {
        return withContext(IO)
        {
            repository3.getAllMangasIDInCurrCategory(id.catID)
        }
    }


}

