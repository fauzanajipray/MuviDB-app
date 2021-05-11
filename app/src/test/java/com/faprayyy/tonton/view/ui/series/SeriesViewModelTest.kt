package com.faprayyy.tonton.view.ui.series

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.local.repository.MuviDBRepository
import com.faprayyy.tonton.data.local.response.SeriesModel
import com.faprayyy.tonton.utils.DataDummy.generateSeriesList
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

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
        val seriesList = viewModel.getSeriesList().value

        verify(muviDBRepository).getSeriesFromApi()
        assertNotNull(seriesList)
        assertEquals(15, seriesList?.size)

        viewModel.getSeriesList().observeForever(observer)
        verify(observer).onChanged(dummySeriesList)
    }
}