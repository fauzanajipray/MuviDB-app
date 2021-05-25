package com.faprayyy.tonton.di

import android.app.Application
import androidx.room.Room
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.local.LocalDataSource
import com.faprayyy.tonton.data.local.db.MuviDao
import com.faprayyy.tonton.data.local.db.MuviDatabase
import com.faprayyy.tonton.data.remote.ApiConfig
import com.faprayyy.tonton.data.remote.ApiService
import com.faprayyy.tonton.data.remote.RemoteDataSource
import com.faprayyy.tonton.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService() : ApiService {
        return ApiConfig.getApiService()
    }

    private const val  DATABASE_NAME = "favorite.db"

    @Provides
    @Singleton
    fun providedeAppDatabase(app : Application) : MuviDatabase {
        return Room.databaseBuilder(
            app,
            MuviDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserFavoriteDao(appDatabase: MuviDatabase) : MuviDao {
        return appDatabase.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        muviDao: MuviDao
    ) : LocalDataSource {
        return LocalDataSource(muviDao)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        herbifyDao: MuviDao
    ) : RemoteDataSource {
        return RemoteDataSource(provideApiService())
    }

    @Provides
    @Singleton
    fun provideExecutorService() : AppExecutors = AppExecutors()


    @Provides
    @Singleton
    fun provideMuviDBRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        appExecutors: AppExecutors
    ) : MuviDBRepository {
        return MuviDBRepository(remoteDataSource, localDataSource, appExecutors)
    }


}