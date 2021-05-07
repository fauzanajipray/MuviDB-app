package com.faprayyy.tonton.view.ui.detailseries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.Response.SeriesDetail
import com.faprayyy.tonton.data.Response.SeriesDetailResponse
import com.faprayyy.tonton.mock
import com.faprayyy.tonton.utils.getListSeriesDetail
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

class DetailSeriesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<SeriesDetail> = mock()
    private lateinit var viewModel: DetailSeriesViewModel
    private lateinit var response: SeriesDetailResponse
    @Before
    fun setup(){
        viewModel = DetailSeriesViewModel()
        viewModel.seriesDetail.observeForever(observer)
        val dataJsonString = getListSeriesDetail()
        response = Gson().fromJson(dataJsonString, SeriesDetailResponse::class.java)
    }

    @Test
    fun setDataJson() {
        val expect = response.results[0]

        viewModel.setDataJson(88396)
        val captor = ArgumentCaptor.forClass(SeriesDetail::class.java)
        captor.run {
            Mockito.verify(observer, Mockito.times(1)).onChanged(capture())
            println(value)
            assertEquals(expect, value)
        }
    }
}