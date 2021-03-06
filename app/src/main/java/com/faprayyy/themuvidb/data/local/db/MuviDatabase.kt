package com.faprayyy.themuvidb.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.data.local.entity.MovieEntity
import com.faprayyy.themuvidb.data.local.entity.SeriesEntity


@Database(entities = [FavoriteEntity::class, MovieEntity::class, SeriesEntity::class], version = 1, exportSchema = false)
abstract class MuviDatabase : RoomDatabase() {

    abstract fun favoriteDao(): MuviDao

}