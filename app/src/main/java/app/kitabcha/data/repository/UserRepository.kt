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

    suspend fun getUser(
        userName: String,
        userPassword: String,
    ): UserEntity?

    suspend fun getAllUserNames(): Flow<List<String>>

    suspend fun getUserFromID(usrID: Int):UserEntity
}

class UserRepositoryImpl
    @Inject
    constructor(
        private val dao: UserDao,
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

        override suspend fun getUser(
            userName: String,
            userPassword: String,
        ): UserEntity? {
            return withContext(IO) {
                dao.getUser(userName, userPassword)
            }
        }

        override suspend fun getAllUserNames(): Flow<List<String>> {
            return withContext(IO) {
                dao.getAllUserNames()
            }
        }

        override suspend fun getUserFromID(usrID: Int): UserEntity {
            return withContext(IO) {
                dao.getUserFromID(usrID)
            }
        }


    }
