package com.faprayyy.tonton.view.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.MuviDBRepository

class FavoriteViewModel(private val mMuviDBRepository: MuviDBRepository) : ViewModel() {

    fun getfavoriteListFromDB() : LiveData<PagedList<FavoriteEntity>> {
        return LivePagedListBuilder(mMuviDBRepository.getFavorite(), 20).build()
    }

}