package com.faprayyy.tonton.data.local

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.faprayyy.tonton.data.local.db.MuviDao
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.local.entity.MovieEntity
import com.faprayyy.tonton.data.local.entity.SeriesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mMuviDao: MuviDao) {

    fun getAllMovie(): DataSource.Factory<Int, MovieEntity> = mMuviDao.getMovie()

    fun insertMovie(movie : List<MovieEntity>) = mMuviDao.insertMovie(movie)

    fun getAllSeries(): DataSource.Factory<Int, SeriesEntity> = mMuviDao.getSeries()

    fun insertSeries(series : List<SeriesEntity>) = mMuviDao.insertSeries(series)

    fun getAllFavorite(query: SupportSQLiteQuery): DataSource.Factory<Int, FavoriteEntity> {
        return mMuviDao.readAllData(query)
    }

    fun getFavoriteByIdAndType(id : Int, type: String): FavoriteEntity {
        return mMuviDao.readDataByIdAndType(id, type)
    }

    fun insert(fav: FavoriteEntity) {
         mMuviDao.addFavorite(fav)
    }

    fun delete(id: Int, type: String) {
         mMuviDao.deleteFavorite(id, type)
    }


}