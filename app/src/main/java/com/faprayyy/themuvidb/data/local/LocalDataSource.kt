package com.faprayyy.themuvidb.data.local

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.faprayyy.themuvidb.data.local.db.MuviDao
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.data.local.entity.MovieEntity
import com.faprayyy.themuvidb.data.local.entity.SeriesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mMuviDao: MuviDao) {

    fun getAllMovie(): DataSource.Factory<Int, MovieEntity> = mMuviDao.getMovie()

    fun insertMovie(movie : List<MovieEntity>) = mMuviDao.insertMovie(movie)

    fun getAllSeries(): DataSource.Factory<Int, SeriesEntity> = mMuviDao.getSeries()

    fun insertSeries(series : List<SeriesEntity>) = mMuviDao.insertSeries(series)

    fun getAllFavorite(query: SupportSQLiteQuery): DataSource.Factory<Int, FavoriteEntity> {
        return mMuviDao.getAllFavorite(query)
    }

    fun getFavoriteByIdAndType(id : Int, type: String): FavoriteEntity {
        return mMuviDao.getFavoriteByIdAndType(id, type)
    }

    fun insertFavorite(fav: FavoriteEntity) {
         mMuviDao.addFavorite(fav)
    }

    fun deleteFavorite(id: Int, type: String) {
         mMuviDao.deleteFavorite(id, type)
    }


}