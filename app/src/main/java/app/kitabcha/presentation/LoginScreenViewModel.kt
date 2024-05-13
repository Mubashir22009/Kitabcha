package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.LibraryEntity
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.repository.UserRepository
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.data.repository.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: UserRepository,
    private val libraryRepository: LibraryRepository
) : ViewModel() {

    fun insertUser(user: UserEntity) {
        viewModelScope.launch(IO) {
            repository.insert(user)
            val usrID = repository.getUser(user.userName,user.password)
            if (usrID != null) {
                libraryRepository.insert(
                    LibraryEntity(
                        ownerUserID = usrID.id
                    )
                )
            }
        }
    }

    fun loginUser(userName: String, password: String, callback: (UserEntity?) -> Unit) {
        viewModelScope.launch {
            callback(repository.getUser(userName, password))
        }
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

