package com.faprayyy.tonton.view.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.local.entity.MovieEntity
import com.faprayyy.tonton.data.local.entity.SeriesEntity
import com.faprayyy.tonton.utils.DataDummy
import com.faprayyy.tonton.utils.PagedListUtil
import com.faprayyy.tonton.utils.PagedTestDataSources
import com.faprayyy.tonton.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    private lateinit var viewModel :  MainViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MuviDBRepository

    @Mock
    private lateinit var observerMovie: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var observerSeries: Observer<Resource<PagedList<SeriesEntity>>>

    private val dummyMovieList = DataDummy.generateMovieEntityList()
    private val dummySeriesList = DataDummy.generateSeriesEntityList()

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun getMovieList() {
        val expected = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        expected.value = Resource.success(PagedTestDataSources.snapshotMovieEntity(dummyMovieList))

        `when`(repository.getMovieFromApi()).thenReturn(expected)

        viewModel.getMovieList().observeForever(observerMovie)

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(dummyMovieList))

        verify(observerMovie).onChanged(expected.value)
        verify(repository).getMovieFromApi()

        val expectedValue = (expected.value)?.data
        val actualValue = (viewModel.getMovieList().value)?.data

        assertEquals(expectedValue, actualValue)
        assertNotNull(movieEntity)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(dummyMovieList.size, movieEntity.data?.size)
    }

    @Test
    fun getSeriesList() {
        val expected = MutableLiveData<Resource<PagedList<SeriesEntity>>>()
        expected.value = Resource.success(PagedTestDataSources.snapshotSeriesEntity(dummySeriesList))

        `when`(repository.getSeriesFromApi()).thenReturn(expected)

        viewModel.getSeriesList().observeForever(observerSeries)

        val seriesEntity = Resource.success(PagedListUtil.mockPagedList(dummySeriesList))

        verify(observerSeries).onChanged(expected.value)
        verify(repository).getSeriesFromApi()

        val expectedValue = (expected.value)?.data
        val actualValue = (viewModel.getSeriesList().value)?.data

        assertEquals(expectedValue, actualValue)
        assertNotNull(seriesEntity)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(dummySeriesList.size, seriesEntity.data?.size)
    }


}