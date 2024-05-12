package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.library
//package app.kitabcha.library

import app.kitabcha.data.repository.CategoryRepository
import app.kitabcha.data.repository.LibraryRepository



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.CategoryEntity
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.repository.UserRepository
import app.kitabcha.data.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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



}

