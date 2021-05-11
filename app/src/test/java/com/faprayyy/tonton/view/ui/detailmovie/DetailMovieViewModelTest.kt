package com.faprayyy.tonton.view.ui.detailmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.Response.MovieDetail
import com.faprayyy.tonton.data.local.repository.MuviDBRepository
import com.faprayyy.tonton.utils.DataDummy.generateMoviesDetailList
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private lateinit var viewModel: DetailMovieViewModel
    private val dummyMovie = generateMoviesDetailList()[0]
    private val movieId = dummyMovie.id as Int
    @get:Rule

    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var muviDBRepository: MuviDBRepository

    @Mock
    private lateinit var observer: Observer<MovieDetail>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(muviDBRepository)
    }

    @Test
    fun getMovie() {
        val movie = MutableLiveData<MovieDetail>()
        movie.value = dummyMovie

        Mockito.`when`(muviDBRepository.getDetailMovieFromApi(movieId)).thenReturn(movie)
        val movieDetail = viewModel.getMovie(movieId).value as MovieDetail
        Assert.assertNotNull(movieDetail)
        Assert.assertEquals(dummyMovie.id, movieDetail.id)
        Assert.assertEquals(dummyMovie.title, movieDetail.title)
        Assert.assertEquals(dummyMovie.overview, movieDetail.overview)

        viewModel.getMovie(movieId).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)
    }
}