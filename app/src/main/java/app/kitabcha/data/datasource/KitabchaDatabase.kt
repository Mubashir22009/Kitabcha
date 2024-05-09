package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import app.kitabcha.data.datasource.LibraryDao
import app.kitabcha.data.datasource.MangaDao
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.data.datasource.UserDao
import app.kitabcha.data.entity.LibraryEntity
import app.kitabcha.data.entity.MangaEntity


@Database(
    entities = [UserEntity::class, MangaEntity::class, LibraryEntity::class],
    version= 1, exportSchema = false
)
abstract class KitabchaDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val mangaDao: MangaDao
    abstract val libraryDao: LibraryDao
}