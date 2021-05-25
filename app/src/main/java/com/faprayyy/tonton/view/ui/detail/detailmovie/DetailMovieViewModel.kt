package com.faprayyy.tonton.view.ui.detail.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.local.entity.MovieEntity
import com.faprayyy.tonton.data.remote.response.MovieDetail
import com.faprayyy.tonton.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val repository: MuviDBRepository
) : ViewModel() {

    fun getDetailMovie(movieId : Int) : LiveData<Resource<MovieDetail>>{
        return repository.getDetailMovieFromApi(movieId)
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