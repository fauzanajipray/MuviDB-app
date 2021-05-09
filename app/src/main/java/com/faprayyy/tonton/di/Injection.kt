package com.faprayyy.tonton.di

import com.faprayyy.tonton.data.local.repository.MuviDBRepository
import com.faprayyy.tonton.data.remote.ApiConfig

object Injection {

    fun provideRepository(): MuviDBRepository {

        val apiService = ApiConfig.getApiService()

        return MuviDBRepository.getInstance(apiService)
    }
}