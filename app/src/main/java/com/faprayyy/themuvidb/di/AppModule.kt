package com.faprayyy.themuvidb.di

import android.app.Application
import androidx.room.Room
import com.faprayyy.themuvidb.data.MuviDBRepository
import com.faprayyy.themuvidb.data.local.LocalDataSource
import com.faprayyy.themuvidb.data.local.db.MuviDao
import com.faprayyy.themuvidb.data.local.db.MuviDatabase
import com.faprayyy.themuvidb.data.remote.ApiConfig
import com.faprayyy.themuvidb.data.remote.ApiService
import com.faprayyy.themuvidb.data.remote.RemoteDataSource
import com.faprayyy.themuvidb.utils.AppExecutors
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
        apiService: ApiService
    ) : RemoteDataSource {
        return RemoteDataSource(apiService)
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