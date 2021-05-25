package com.faprayyy.tonton.view.ui.series

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.remote.response.SeriesModel
import com.faprayyy.tonton.utils.DataDummy.generateSeriesList
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/*
@RunWith(MockitoJUnitRunner::class)
class SeriesViewModelTest {

    private lateinit var viewModel: SeriesViewModel
    @get:Rule

    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var muviDBRepository: MuviDBRepository

    @Mock
    private lateinit var observer: Observer<ArrayList<SeriesModel>>

    @Before
    fun setUp() {
        viewModel = SeriesViewModel(muviDBRepository)
    }

    @Test
    fun getSeriesList() {
        val dummySeriesList = generateSeriesList()
        val seriesListLive = MutableLiveData<ArrayList<SeriesModel>>()
        seriesListLive.value = dummySeriesList

        `when`(muviDBRepository.getSeriesFromApi()).thenReturn(seriesListLive)
        viewModel.getSeriesListFromApi()
        val seriesList = viewModel.getSeriesList().value

        verify(muviDBRepository).getSeriesFromApi()
        assertNotNull(seriesList)
        assertEquals(dummySeriesList.size, seriesList?.size)

        viewModel.getSeriesList().observeForever(observer)
        verify(observer).onChanged(dummySeriesList)
    }
}

 */