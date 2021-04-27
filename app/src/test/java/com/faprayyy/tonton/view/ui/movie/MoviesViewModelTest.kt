package com.faprayyy.tonton.view.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.MovieModel
import com.faprayyy.tonton.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class MoviesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<ArrayList<MovieModel>> = mock()
    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup(){
        viewModel = MoviesViewModel()
        viewModel.listMovie.observeForever(observer)
    }

    @Test
    fun getData() {
        viewModel.getData()
        val arrayList = ArrayList<MovieModel>()
        val captor = ArgumentCaptor.forClass(arrayList::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            println(value.size)
            assertEquals(20, value.size)
        }
    }

}