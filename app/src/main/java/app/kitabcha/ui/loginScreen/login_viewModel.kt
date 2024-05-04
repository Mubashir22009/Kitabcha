package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.ui.loginScreen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.MainDatabase
import app.kitabcha.data.User
import app.kitabcha.ui.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class login_viewModel(application: Application): AndroidViewModel(application) {


    private val repository: Repository

    init{
        val userdao=MainDatabase.getDataBase(application).userDao()
        repository=Repository(userdao)
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }
}

