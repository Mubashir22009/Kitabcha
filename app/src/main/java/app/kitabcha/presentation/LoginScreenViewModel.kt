package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.LibraryEntity
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.data.repository.LibraryRepository
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel
    @Inject
    constructor(
        private val repository: UserRepository,
        private val libraryRepository: LibraryRepository,
    ) : ViewModel() {
        fun insertUser(user: UserEntity) {
            viewModelScope.launch(IO) {
                val totalBefore = repository.getTotalUserCount()
                repository.insert(user)
                val usrID = repository.getUser(user.userName, user.password)
                if (usrID != null) {
                    libraryRepository.insert(
                        LibraryEntity(
                            ownerUserID = usrID.id,
                        ),
                    )
                }
                val totalNow = repository.getTotalUserCount()
                if (totalNow == totalBefore) {
                    _alreadyExists.tryEmit(true)
                }
            }
        }

        fun loginUser(
            userName: String,
            password: String,
            callback: (UserEntity?) -> Unit,
        ) {
            viewModelScope.launch {
                callback(repository.getUser(userName, password))
            }
        }

        private val _userName = MutableStateFlow("")
        val userName = _userName.asStateFlow()

        private val _alreadyExists = MutableStateFlow(false)
        val alreadyExists = _alreadyExists.asStateFlow()

        fun setUserName(name: String) {
            _userName.tryEmit(name)
        }

        private val _userPassword = MutableStateFlow("")
        val userPassword = _userPassword.asStateFlow()

        fun setUserPassword(name: String) {
            _userPassword.tryEmit(name)
        }

        fun resetExistFlag() {
            _alreadyExists.tryEmit(false)
        }
    }
