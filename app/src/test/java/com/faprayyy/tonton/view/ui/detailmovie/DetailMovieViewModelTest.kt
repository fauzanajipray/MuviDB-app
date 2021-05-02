package com.faprayyy.tonton.view.ui.detailmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.Response.MovieDetail
import com.faprayyy.tonton.data.Response.MovieDetailResponse
import com.faprayyy.tonton.mock
import com.faprayyy.tonton.utils.getListMovieDetail
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class DetailMovieViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<MovieDetail> = mock()
    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var response: MovieDetailResponse
    @Before
    fun setup(){
        viewModel = DetailMovieViewModel()
        viewModel.movieDetail.observeForever(observer)
        val dataJsonString = getListMovieDetail()
        response = Gson().fromJson(dataJsonString, MovieDetailResponse::class.java)
    }

    @Test
    fun setDataJson() {
        val expect = response.results[0]

        viewModel.setDataJson(460465)
        val captor = ArgumentCaptor.forClass(MovieDetail::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            println(value)
            assertEquals(expect, value)
        }
    }
}