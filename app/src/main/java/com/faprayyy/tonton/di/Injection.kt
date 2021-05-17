package com.faprayyy.tonton.di

import android.content.Context
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.local.LocalDataSource
import com.faprayyy.tonton.data.local.db.FavoriteDatabase
import com.faprayyy.tonton.data.remote.ApiConfig
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Injection {

    fun provideRepository(context: Context): MuviDBRepository {

        val database = FavoriteDatabase.getDatabase(context)

        val apiService = ApiConfig.getApiService()
        val localDataSource = LocalDataSource.getInstance(database.favoriteDao())

        val executorService: ExecutorService = Executors.newSingleThreadExecutor()

        return MuviDBRepository.getInstance(apiService, localDataSource, executorService)
    }

}