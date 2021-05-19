package com.faprayyy.tonton.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.local.LocalDataSource
import com.faprayyy.tonton.data.remote.ApiService
import com.faprayyy.tonton.data.remote.response.DiscoverMovieResponse
import com.faprayyy.tonton.data.remote.response.MovieModel
import com.faprayyy.tonton.utils.CallFake
import com.faprayyy.tonton.utils.DataDummy
import com.faprayyy.tonton.utils.LiveDataTestUtils
import com.faprayyy.tonton.utils.SortUtils
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import java.util.concurrent.Executors
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MuviDBRepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

//    private val remote = mock(ApiServiceMock::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = Executors.newSingleThreadExecutor()
    private var apiResponse = mock(ApiService::class.java)
    private lateinit var muviDBRepository : MuviDBRepository


    private val dummyMovieList = DataDummy.generateMoviesList()
    private val movieId = dummyMovieList[0].id
    private val movieDetail = DataDummy.generateDetailMovie()
    private val dummySeriesList = DataDummy.generateMoviesList()
    private val seriesId = dummySeriesList[0].id
    private val seriesDetail = DataDummy.generateDetailSeries()
    private val stringSort = SortUtils.NEWEST

    @Mock
    private lateinit var observer: Observer<ArrayList<MovieModel>>


    @Before
    fun setUp() {
        muviDBRepository = MuviDBRepository.getInstance(apiResponse,local,appExecutors)
    }

    @Test
    fun `get discover movies and should return success`(){
        val dummyListMovie = MutableLiveData<ArrayList<MovieModel>>()
        dummyListMovie.value = dummyMovieList

        `when`(muviDBRepository.getMovieFromApi()).thenReturn(dummyListMovie)
        val resultRemote = muviDBRepository.getMovieFromApi().value

        verify(muviDBRepository).getMovieFromApi()

        println(resultRemote)
        assertNotNull(resultRemote)
        assertEquals(dummyMovieList.size, resultRemote?.size)
        muviDBRepository.getMovieFromApi().observeForever(observer)
        verify(observer).onChanged(dummyMovieList)

    }


}