package com.faprayyy.tonton.view.ui.detail.detailseries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.remote.response.MovieDetail
import com.faprayyy.tonton.data.remote.response.SeriesDetail
import com.faprayyy.tonton.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailSeriesViewModel @Inject constructor(
    private val repository: MuviDBRepository
) : ViewModel() {

    fun getDetailSeries(seriesId : Int) : LiveData<Resource<SeriesDetail>>{
        return repository.getDetailSeriesFromApi(seriesId)
    }

    fun setFavorite(favoriteEntity: FavoriteEntity) {
        repository.setFavorite(favoriteEntity)
    }
    fun deleteFavorite(id : Int, type : String) {
        repository.deleteFavorite(id, type)
    }

    fun getSeriesFromDB(id : Int, type : String) : LiveData<FavoriteEntity> {
        return repository.getFavoriteById(id, type)
    }
}