package com.faprayyy.themuvidb.data.local.db

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.data.local.entity.MovieEntity
import com.faprayyy.themuvidb.data.local.entity.SeriesEntity

@Dao
interface MuviDao {

    @Query("SELECT * FROM movies")
    fun getMovie(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @Query("SELECT * FROM series")
    fun getSeries(): DataSource.Factory<Int, SeriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeries(series: List<SeriesEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(fav: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE id = :id AND type = :type")
    fun deleteFavorite(id: Int, type: String)

    @Query("SELECT * FROM favorite WHERE id = :id AND type = :type LIMIT 1")
    fun getFavoriteByIdAndType(id: Int, type : String): FavoriteEntity

    @RawQuery(observedEntities = [FavoriteEntity::class])
    fun getAllFavorite(query: SupportSQLiteQuery): DataSource.Factory<Int, FavoriteEntity>
}