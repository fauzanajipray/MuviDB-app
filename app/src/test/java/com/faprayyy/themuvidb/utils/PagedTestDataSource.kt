package com.faprayyy.themuvidb.utils

import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.data.local.entity.MovieEntity
import com.faprayyy.themuvidb.data.local.entity.SeriesEntity
import java.util.concurrent.Executors

class PagedTestDataSources<T> private constructor(private val items: List<T>) : PositionalDataSource<T>() {
    companion object {
        fun snapshotMovieEntity(items: List<MovieEntity> = listOf()): PagedList<MovieEntity> {
            return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
        }
        fun snapshotSeriesEntity(items: List<SeriesEntity> = listOf()): PagedList<SeriesEntity> {
            return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
        }
        fun snapshotFavoriteEntity(items: List<FavoriteEntity> = listOf()): PagedList<FavoriteEntity> {
            return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        callback.onResult(items, 0, items.size)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        val start = params.startPosition
        val end = params.startPosition + params.loadSize
        callback.onResult(items.subList(start, end))
    }
}