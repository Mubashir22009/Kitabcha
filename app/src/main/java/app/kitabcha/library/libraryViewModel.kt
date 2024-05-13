package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.library
//package app.kitabcha.library

import app.kitabcha.data.repository.CategoryRepository
import app.kitabcha.data.repository.LibraryRepository



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.CategoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject



@HiltViewModel
class libraryScreenViewModel @Inject constructor(
    private val repository: CategoryRepository,

    private val repository2: LibraryRepository
) : ViewModel() {

    fun insertCategory(id: CategoryEntity) {
        viewModelScope.launch(IO) {
            repository.insert(id)
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
    fun getCategoryIdUsingCategoryId


}

