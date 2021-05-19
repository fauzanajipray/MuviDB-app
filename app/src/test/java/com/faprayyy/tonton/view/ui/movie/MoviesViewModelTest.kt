package com.faprayyy.tonton.view.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.remote.response.MovieModel
import com.faprayyy.tonton.utils.DataDummy.generateMoviesList
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest{

    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var muviDBRepository: MuviDBRepository

    @Mock
    private lateinit var observer: Observer<ArrayList<MovieModel>>

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(muviDBRepository)
    }

    @Test
    fun getMoviesList(){
        val dummyMovieList = generateMoviesList()
        val moviesListLive = MutableLiveData<ArrayList<MovieModel>>()
        moviesListLive.value = dummyMovieList

        `when`(muviDBRepository.getMovieFromApi()).thenReturn(moviesListLive)
        viewModel.getMoviesListFromApi()
        val moviesList = viewModel.getMoviesList().value

        verify(muviDBRepository).getMovieFromApi()
        Assert.assertNotNull(moviesList)
        Assert.assertEquals(20, moviesList?.size)

        viewModel.getMoviesList().observeForever(observer)
        verify(observer).onChanged(dummyMovieList)
    }
}