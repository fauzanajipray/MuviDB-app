package com.faprayyy.themuvidb.view.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.themuvidb.data.MuviDBRepository
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.data.remote.response.MovieDetail
import com.faprayyy.themuvidb.data.remote.response.SeriesDetail
import com.faprayyy.themuvidb.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(
        private val repository: MuviDBRepository
) : ViewModel(){

    fun getDetailMovie(movieId : Int) : LiveData<Resource<MovieDetail>> {
        return repository.getDetailMovieFromApi(movieId)
    }

    fun getDetailSeries(seriesId : Int) : LiveData<Resource<SeriesDetail>>{
        return repository.getDetailSeriesFromApi(seriesId)
    }

    fun setFavorite(favoriteEntity: FavoriteEntity) {
        repository.setFavorite(favoriteEntity)
    }

    fun deleteFavorite(id : Int, type : String) {
        repository.deleteFavorite(id, type)
    }

    fun getMovieFromDB(id : Int, type : String) : LiveData<FavoriteEntity> {
        return repository.getFavoriteById(id, type)
    }

}