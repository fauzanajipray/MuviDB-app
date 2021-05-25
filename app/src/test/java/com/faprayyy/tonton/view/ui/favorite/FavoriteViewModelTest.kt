package com.faprayyy.tonton.view.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.utils.DataDummy
import com.faprayyy.tonton.utils.SortUtils
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest{
    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var muviDBRepository: MuviDBRepository

    @Mock
    private lateinit var observer: Observer<PagedList<FavoriteEntity>>

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(muviDBRepository)
    }

    @Test
    fun getFavoriteList(){

        val expected = MutableLiveData<PagedList<FavoriteEntity>>()
        expected.value = PagedTestDataSources.snapshot(DataDummy.generateFavEntityList())

        Mockito.`when`(muviDBRepository.getFavorites(SortUtils.NEWEST)).thenReturn(expected)

        viewModel.getFavoriteListFromDB().observeForever(observer)
        Mockito.verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteListFromDB().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(expectedValue?.size, actualValue?.size)
    }

    class PagedTestDataSources private constructor(private val items: List<FavoriteEntity>) : PositionalDataSource<FavoriteEntity>() {
        companion object {
            fun snapshot(items: List<FavoriteEntity> = listOf()): PagedList<FavoriteEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                        .setNotifyExecutor(Executors.newSingleThreadExecutor())
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .build()
            }
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<FavoriteEntity>) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<FavoriteEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}