package com.faprayyy.themuvidb.view.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.faprayyy.themuvidb.data.MuviDBRepository
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.utils.SortUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val mMuviDBRepository: MuviDBRepository
) : ViewModel() {

    fun getFavoriteListFromDB(sort: String) : LiveData<PagedList<FavoriteEntity>> {
        return mMuviDBRepository.getFavorites(sort)
    }

    fun getFavoriteListFromDB() : LiveData<PagedList<FavoriteEntity>> {
        val sort = SortUtils.NEWEST
        return mMuviDBRepository.getFavorites(sort)
    }

}