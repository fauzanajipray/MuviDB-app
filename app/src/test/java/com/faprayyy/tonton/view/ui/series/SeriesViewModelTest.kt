package com.faprayyy.tonton.view.ui.series

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.SeriesModel
import com.faprayyy.tonton.mock
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class SeriesViewModelTest{
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<ArrayList<SeriesModel>> = mock()
    private lateinit var viewModel: SeriesViewModel

    @Before
    fun setup(){
        viewModel = SeriesViewModel()
        viewModel.listSeries.observeForever(observer)
    }

    @Test
    fun getData() {
        viewModel.getData()
        val arrayList = ArrayList<SeriesModel>()
        val captor = ArgumentCaptor.forClass(arrayList::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            println(value.size)
            assertEquals(15, value.size)
        }
    }
}