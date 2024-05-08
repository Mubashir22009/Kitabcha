package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.repository.UserRepository
import app.kitabcha.data.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun insertUser(user: UserEntity) {
        viewModelScope.launch(IO) {
            repository.insert(user)
        }
    }


    fun forAccountExistence(userName: String, userPassword: String): Int?
    {
        var lst: List<UserEntity?>
        lst=repository.forAccountExistence(userName,userPassword)
        if(lst.size==0)
        {
            // TODO: show error that you are loging in but you dont have account
            return null
        }
        else
        {
            return lst.first()!!.id
        }
        return null
    }

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()
    fun setUserName(name: String) {
        _userName.tryEmit(name)
    }

    private val _userPassword = MutableStateFlow("")
    val userPassword = _userPassword.asStateFlow()
    fun setUserPassword(name: String) {
        _userPassword.tryEmit(name)
    }



}

