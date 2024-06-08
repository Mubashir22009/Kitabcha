package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import app.kitabcha.data.datasource.CategoryDao
import app.kitabcha.data.datasource.CategoryMangaDao
import app.kitabcha.data.datasource.ChapterDao
import app.kitabcha.data.datasource.LibraryDao
import app.kitabcha.data.datasource.MangaDao
import app.kitabcha.data.datasource.UserDao
import app.kitabcha.data.datasource.UserReadStatusDao
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.CategoryMangaEntity
import app.kitabcha.data.entity.ChapterEntity
import app.kitabcha.data.entity.LibraryEntity
import app.kitabcha.data.entity.MangaEntity
import app.kitabcha.data.entity.UserEntity
import app.kitabcha.data.entity.UserReadStatusEntity

@Database(
    entities = [
        UserEntity::class, MangaEntity::class, LibraryEntity::class,
        CategoryEntity::class, CategoryMangaEntity::class, ChapterEntity::class,
        UserReadStatusEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class KitabchaDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val mangaDao: MangaDao
    abstract val libraryDao: LibraryDao
    abstract val categoryDao: CategoryDao
    abstract val categorymangaDao: CategoryMangaDao
    abstract val chapterDao: ChapterDao
    abstract val userreadstatusDao: UserReadStatusDao
}
