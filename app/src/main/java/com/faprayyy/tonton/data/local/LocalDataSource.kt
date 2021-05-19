package com.faprayyy.tonton.data.local

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.faprayyy.tonton.data.local.db.FavoriteDao
import com.faprayyy.tonton.data.local.entity.FavoriteEntity

class LocalDataSource private constructor(private val mFavoriteDao: FavoriteDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(mFavoriteDao: FavoriteDao): LocalDataSource =
                INSTANCE ?: LocalDataSource(mFavoriteDao).apply {
                    INSTANCE = this
                }

    }

    fun getAllFavorite(query: SupportSQLiteQuery): DataSource.Factory<Int, FavoriteEntity> {
        return mFavoriteDao.readAllData(query)
    }

    fun getFavoriteByIdAndType(id : Int, type: String): FavoriteEntity {
        return mFavoriteDao.readDataByIdAndType(id, type)
    }

    fun insert(fav: FavoriteEntity) {
         mFavoriteDao.addFavorite(fav)
    }

    fun delete(id: Int, type: String) {
         mFavoriteDao.deleteFavorite(id, type)
    }

}