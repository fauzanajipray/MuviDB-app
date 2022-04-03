package com.faprayyy.themuvidb.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.faprayyy.themuvidb.data.local.LocalDataSource
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.data.local.entity.MovieEntity
import com.faprayyy.themuvidb.data.local.entity.SeriesEntity
import com.faprayyy.themuvidb.data.remote.RemoteDataSource
import com.faprayyy.themuvidb.data.remote.response.MovieDetail
import com.faprayyy.themuvidb.data.remote.response.SeriesDetail
import com.faprayyy.themuvidb.utils.*
import com.faprayyy.themuvidb.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import java.util.concurrent.Executor

class MuviDBRepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val executor = Executor{ it.run() }
    private val appExecutors = AppExecutors(executor, executor)
    private val repository = MuviDBRepository(remote,local,appExecutors)

    private val stringSort = SortUtils.NEWEST
    private val dummyMovieList = DataDummy.generateMoviesList()
    private val dummySeriesList = DataDummy.generateMoviesList()
    private val dummyFavEntity = DataDummy.generateFavEntity(stringSort)
    private val movieId = dummyMovieList[0].id
    private val seriesId = dummySeriesList[0].id
    private val movieDetail : MovieDetail = DataDummy.generateDetailMovie()
    private val seriesDetail : SeriesDetail = DataDummy.generateDetailSeries()

    @Test
    fun `get discover movies and should return success`(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovie()).thenReturn(dataSourceFactory)
        repository.getMovieFromApi()

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(dummyMovieList))

        verify(local).getAllMovie()
        assertNotNull(movieEntity)
        assertEquals(dummyMovieList.size, movieEntity.data?.size)
    }

    @Test
    fun `get discover series and should return success`(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, SeriesEntity>
        `when`(local.getAllSeries()).thenReturn(dataSourceFactory)
        repository.getSeriesFromApi()

        val seriesEntity = Resource.success(PagedListUtil.mockPagedList(dummyMovieList))

        verify(local).getAllSeries()
        assertNotNull(seriesEntity)
        assertEquals(dummyMovieList.size, seriesEntity.data?.size)
    }


    @Test
    fun `get detail movie should return success`(){
        val movieDetailFactory = MutableLiveData<Resource<MovieDetail>>()
        movieDetailFactory.value = Resource.success(movieDetail)

        `when`(remote.getDetailMovie(movieId)).thenReturn(movieDetailFactory)

        val movieDetailResource = LiveDataTestUtils.getValue(repository.getDetailMovieFromApi(movieId))

        verify(remote).getDetailMovie(movieId)
        assertNotNull(movieDetailResource)
        assertEquals(movieDetail, movieDetailResource.data)
    }

    @Test
    fun `get detail series should return success`(){
        val movieDetailFactory = MutableLiveData<Resource<SeriesDetail>>()
        movieDetailFactory.value = Resource.success(seriesDetail)

        `when`(remote.getDetailSeries(seriesId)).thenReturn(movieDetailFactory)

        val seriesDetailResource = LiveDataTestUtils.getValue(repository.getDetailSeriesFromApi(seriesId))

        verify(remote).getDetailSeries(seriesId)
        assertNotNull(seriesDetailResource)
        assertEquals(seriesDetail, seriesDetailResource.data)
    }

    @Test
    fun `set favorite should return success`(){

        repository.setFavorite(dummyFavEntity)
        verify(local, times(1)).insertFavorite(dummyFavEntity)

    }

    @Test
    fun `delete favorite should return success`(){
        val id = dummyFavEntity.id
        val type = dummyFavEntity.type
        repository.deleteFavorite(id, type)

        verify(local, times(1)).deleteFavorite(id, type)

    }

    @Test
    fun `get favorite from DB should return success`(){
        val favoriteFactory = MutableLiveData<FavoriteEntity>()
        favoriteFactory.value = dummyFavEntity
        val id = dummyFavEntity.id
        val type = dummyFavEntity.type

        `when`(local.getFavoriteByIdAndType(id, type)).thenReturn(dummyFavEntity)
        repository.getFavoriteById(id, type)

        val favoriteEntity = LiveDataTestUtils.getValue(repository.getFavoriteById(id, type))

        verify(local, times(2)).getFavoriteByIdAndType(id, type)
        assertNotNull(favoriteEntity)
        assertEquals(dummyFavEntity, favoriteEntity)
    }

}