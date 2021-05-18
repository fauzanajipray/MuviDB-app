package com.faprayyy.tonton.view.ui.detailseries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.remote.response.MovieDetail
import com.faprayyy.tonton.data.remote.response.SeriesDetail

class DetailSeriesViewModel(private val mMuviDBRepository: MuviDBRepository) : ViewModel() {

    private var series = MutableLiveData<SeriesDetail>()

    fun getSeriesFromApi(seriesId: Int) {
        val data = mMuviDBRepository.getDetailSeriesFromApi(seriesId)
        series = data as MutableLiveData<SeriesDetail>
    }

    fun getSeries() : LiveData<SeriesDetail> {
        return series
    }

    fun setFavorite(favoriteEntity: FavoriteEntity) {
        mMuviDBRepository.setFavorite(favoriteEntity)
    }
    fun deleteFavorite(id : Int, type : String) {
        mMuviDBRepository.deleteFavorite(id, type)
    }

    fun getSeriesFromDB(id : Int, type : String) : LiveData<FavoriteEntity> {
        return mMuviDBRepository.getFavoriteById(id, type)
    }
}