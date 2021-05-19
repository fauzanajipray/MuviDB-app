package com.faprayyy.tonton.data.local.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.faprayyy.tonton.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(fav: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE id = :id AND type = :type")
    fun deleteFavorite(id: Int, type: String)

    @Query("SELECT * FROM favorite WHERE id = :id AND type = :type LIMIT 1")
    fun readDataByIdAndType(id: Int, type : String): FavoriteEntity

    @RawQuery(observedEntities = [FavoriteEntity::class])
    fun readAllData(query: SupportSQLiteQuery): DataSource.Factory<Int, FavoriteEntity>

}