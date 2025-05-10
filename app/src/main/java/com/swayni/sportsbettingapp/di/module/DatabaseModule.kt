package com.swayni.sportsbettingapp.di.module

import android.content.Context
import androidx.room.Room
import com.swayni.sportsbettingapp.BuildConfig
import com.swayni.sportsbettingapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideSportOddDao(database: AppDatabase) = database.sportOddDao()


}