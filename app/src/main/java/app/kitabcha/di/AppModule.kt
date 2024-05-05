package com.mkrdeveloper.viewmodeljetpack.app.kitabcha.di

import android.app.Application
import androidx.room.Room
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
            "Kitabcha-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: KitabchaDatabase): UserRepository {
        return UserRepositoryImpl(db.userDao)
    }
}