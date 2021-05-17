package com.faprayyy.tonton.view.ui.detailseries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.Response.SeriesDetail
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.utils.DataDummy.generateSeriesDetailList
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailSeriesViewModelTest {

    private lateinit var viewModel: DetailSeriesViewModel
    private val dummySeries = generateSeriesDetailList()[0]
    private val seriesId = dummySeries.id as Int
    @get:Rule

    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var muviDBRepository: MuviDBRepository

    @Mock
    private lateinit var observer: Observer<SeriesDetail>

    @Before
    fun setUp() {
        viewModel = DetailSeriesViewModel(muviDBRepository)
    }

    @Test
    fun getSeries() {
        val series = MutableLiveData<SeriesDetail>()
        series.value = dummySeries

        `when`(muviDBRepository.getDetailSeriesFromApi(seriesId)).thenReturn(series)
        val seriesDetail = viewModel.getSeries(seriesId).value as SeriesDetail
        assertNotNull(seriesDetail)
        assertEquals(dummySeries.id, seriesDetail.id)
        assertEquals(dummySeries.name, seriesDetail.name)
        assertEquals(dummySeries.overview, seriesDetail.overview)

        viewModel.getSeries(seriesId).observeForever(observer)
        verify(observer).onChanged(dummySeries)
    }
}