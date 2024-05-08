package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.data.datasource.UserDao


@Database(
    entities = [UserEntity::class],
    version= 1, exportSchema = false
)
abstract class KitabchaDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}
