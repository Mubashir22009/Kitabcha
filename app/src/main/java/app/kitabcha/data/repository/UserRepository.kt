package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.repository

import app.kitabcha.data.datasource.UserDao
import app.kitabcha.data.entity.UserEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UserRepository {

    suspend fun insert(user: UserEntity)

    suspend fun delete(user: UserEntity)

    suspend fun getPassword(userName: String): Flow<List<String>>

    suspend fun getAllUserNames(): Flow<List<String>>
}

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository {
    override suspend fun insert(user: UserEntity) {
        withContext(IO) {
            dao.insert(user)
        }
    }

    override suspend fun delete(user: UserEntity) {
        withContext(IO) {
            dao.delete(user)
        }
    }

    override suspend fun getPassword(userName: String): Flow<List<String>> {
        return withContext(IO) {
            dao.getPassword(userName)
        }
    }

    override suspend fun getAllUserNames(): Flow<List<String>> {
        return withContext(IO) {
            dao.getAllUserNames()
        }
    }
}