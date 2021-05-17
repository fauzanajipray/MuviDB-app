package com.faprayyy.tonton.view.ui.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.remote.response.MovieDetail

class DetailMovieViewModel(private val mMuviDBRepository: MuviDBRepository) : ViewModel() {

    private var movie = MutableLiveData<MovieDetail>()

    fun getMovieFromApi(movieId : Int) {
        val data = mMuviDBRepository.getDetailMovieFromApi(movieId)
        movie = data as MutableLiveData<MovieDetail>
    }

    fun getMovie(): LiveData<MovieDetail> {
        return movie
    }

    fun setFavorite(favoriteEntity: FavoriteEntity) {
        mMuviDBRepository.setFavorite(favoriteEntity)
    }
    fun deleteFavorite(id : Int, type : String) {
        mMuviDBRepository.deleteFavorite(id, type)
    }

    fun getMovieFromDB(id : Int, type : String) : LiveData<FavoriteEntity> {
        return mMuviDBRepository.getFavoriteById(id, type)
    }



}