package com.swayni.sportsbettingapp.di.module

import com.swayni.sportsbettingapp.data.Api
import com.swayni.sportsbettingapp.data.db.dao.SportOddDao
import com.swayni.sportsbettingapp.data.local.ILocalDataSource
import com.swayni.sportsbettingapp.data.local.LocalDataSource
import com.swayni.sportsbettingapp.data.remote.IRemoteDataSource
import com.swayni.sportsbettingapp.data.remote.RemoteDataSource
import com.swayni.sportsbettingapp.data.repository.IRepository
import com.swayni.sportsbettingapp.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(api : Api): IRemoteDataSource = RemoteDataSource(api)

    @Singleton
    @Provides
    fun provideLocalDataSource(sportOddDao: SportOddDao) : ILocalDataSource = LocalDataSource(sportOddDao)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: IRemoteDataSource, localDataSource: ILocalDataSource): IRepository = Repository(remoteDataSource, localDataSource)


}