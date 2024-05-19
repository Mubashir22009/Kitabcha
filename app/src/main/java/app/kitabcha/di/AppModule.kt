package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.di

import android.app.Application
import androidx.room.Room
import app.kitabcha.data.repository.CategoryMangaRepository
import app.kitabcha.data.repository.CategoryMangaRepositoryImpl
import app.kitabcha.data.repository.CategoryRepository
import app.kitabcha.data.repository.CategoryRepositoryImpl
import app.kitabcha.data.repository.ChapterRepository
import app.kitabcha.data.repository.ChapterRepositoryImpl
import app.kitabcha.data.repository.LibraryRepository
import app.kitabcha.data.repository.LibraryRepositoryImpl
import app.kitabcha.data.repository.MangaRepository
import app.kitabcha.data.repository.MangaRepositoryImpl
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.datasource.KitabchaDatabase
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.repository.UserRepository
import com.mkrdeveloper.viewmodeljetpack.app.kitabcha.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): KitabchaDatabase {
        return Room.databaseBuilder(
            app,
            KitabchaDatabase::class.java,
            "Kitabcha-database",
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: KitabchaDatabase): UserRepository {
        return UserRepositoryImpl(db.userDao)
    }

    @Provides
    @Singleton
    fun provideMangaRepository(db: KitabchaDatabase): MangaRepository {
        return MangaRepositoryImpl(db.mangaDao)
    }

    @Provides
    @Singleton
    fun provideLibraryRepository(db: KitabchaDatabase): LibraryRepository {
        return LibraryRepositoryImpl(db.libraryDao)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(db: KitabchaDatabase): CategoryRepository {
        return CategoryRepositoryImpl(db.categoryDao)
    }

    @Provides
    @Singleton
    fun provideCategoryMangaRepository(db: KitabchaDatabase): CategoryMangaRepository {
        return CategoryMangaRepositoryImpl(db.categorymangaDao)
    }

    @Provides
    @Singleton
    fun provideChapterRepository(db: KitabchaDatabase): ChapterRepository {
        return ChapterRepositoryImpl(db.chapterDao)
    }
}
