package com.faprayyy.tonton.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import com.faprayyy.tonton.data.local.LocalDataSource
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.remote.ApiService
import com.faprayyy.tonton.data.remote.response.DiscoverMovieResponse
import com.faprayyy.tonton.data.remote.response.MovieModel
import com.faprayyy.tonton.utils.*
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
    private var apiService = mock(ApiService::class.java, RETURNS_DEEP_STUBS)
    private lateinit var muviDBRepository : FakeMuviDBRepository

    private val stringSort = SortUtils.NEWEST
    private val dummyMovieList = DataDummy.generateMoviesList()
    private val dummySeriesList = DataDummy.generateMoviesList()
    private val dummyFavList = DataDummy.generateFavEntity(stringSort)
    private val movieId = dummyMovieList[0].id
    private val movieDetail = DataDummy.generateDetailMovie()
    private val seriesId = dummySeriesList[0].id
    private val seriesDetail = DataDummy.generateDetailSeries()

    @Mock
    private lateinit var observer: Observer<ArrayList<MovieModel>>


    @Before
    fun setUp() {
        muviDBRepository = FakeMuviDBRepository(apiService,local,appExecutors)
    }

    @Test
    fun `get discover movies and should return success`(){
//        val liveListMovie = MutableLiveData<ArrayList<MovieModel>>()
//        liveListMovie.value = dummyMovieList
//
//        val response : Response<DiscoverMovieResponse> = Response.success(DiscoverMovieResponse(dummyMovieList))
//        println(response.body())
//        `when`(apiService).thenReturn()
//        `when`(apiService.getDiscoverMovies().execute()).thenReturn(response)
//        val resultRemote = muviDBRepository.getMovieFromApi().value
//
//        verify(apiService.getDiscoverMovies())
//
//        println(resultRemote)
//        assertNotNull(resultRemote)
//        assertEquals(dummyMovieList.size, resultRemote?.size)
//        muviDBRepository.getMovieFromApi().observeForever(observer)
//        verify(observer).onChanged(dummyMovieList)
    }

    @Test
    fun `get favorite list from DB should return success`(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteEntity>

        val query = SortUtils.getSortedQuery(stringSort)
        `when`(local.getAllFavorite(query)).thenReturn(dataSourceFactory)
        muviDBRepository.getFavorites(stringSort)

        val courseEntities = PagedListUtil.mockPagedList(DataDummy.generateFavEntityList())
        com.nhaarman.mockitokotlin2.verify(local).getAllFavorite(query)
        assertNotNull(courseEntities)
        assertEquals(dummyFavList.size.toLong(), courseEntities.data?.size?.toLong())

    }


}