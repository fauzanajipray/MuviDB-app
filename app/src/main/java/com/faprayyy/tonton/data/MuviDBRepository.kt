package com.faprayyy.tonton.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faprayyy.tonton.data.local.LocalDataSource
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.local.entity.MovieEntity
import com.faprayyy.tonton.data.local.entity.SeriesEntity
import com.faprayyy.tonton.data.remote.RemoteDataSource
import com.faprayyy.tonton.data.remote.response.MovieDetail
import com.faprayyy.tonton.data.remote.response.MovieModel
import com.faprayyy.tonton.data.remote.response.SeriesDetail
import com.faprayyy.tonton.data.remote.response.SeriesModel
import com.faprayyy.tonton.utils.AppExecutors
import com.faprayyy.tonton.utils.SortUtils
import com.faprayyy.tonton.vo.Resource
import javax.inject.Inject

class MuviDBRepository @Inject constructor(
        private val mRemoteDataSource: RemoteDataSource,
        private val mLocalDataSource: LocalDataSource,
        private val mExecutor: AppExecutors
): MuviDBDataSource {

    override fun getMovieFromApi(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieModel>>(mExecutor){
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(mLocalDataSource.getAllMovie(), config).build()
            }

            override fun shouldFecth(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<Resource<List<MovieModel>>> {
                return mRemoteDataSource.getDiscoverMovie()
            }

            override fun saveCallResult(data: List<MovieModel>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data){
                    val movie = MovieEntity(
                        response.id,
                        response.voteAverage,
                        response.popularity,
                        response.title,
                        response.posterPath,
                        response.originalLanguage,
                        response.originalTitle,
                        response.backdropPath,
                        response.releaseDate,
                        response.overview)
                    movieList.add( movie )
                }
                mLocalDataSource.insertMovie(movieList)
            }

        }.asLiveData()
    }

    override fun getSeriesFromApi(): LiveData<Resource<PagedList<SeriesEntity>>> {
        return object : NetworkBoundResource<PagedList<SeriesEntity>, List<SeriesModel>>(mExecutor){

            override fun loadFromDB(): LiveData<PagedList<SeriesEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(mLocalDataSource.getAllSeries(), config).build()
            }

            override fun shouldFecth(data: PagedList<SeriesEntity>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<Resource<List<SeriesModel>>> = mRemoteDataSource.getDiscoverSeries()

            override fun saveCallResult(data: List<SeriesModel>) {
                val listSeries = ArrayList<SeriesEntity>()
                for (response in data){
                    val series = SeriesEntity(
                        response.id,
                        response.voteAverage,
                        response.popularity,
                        response.title,
                        response.posterPath,
                        response.originalLanguage,
                        response.originalTitle,
                        response.backdropPath,
                        response.release_date,
                        response.overview
                    )
                    listSeries.add( series )
                }
                mLocalDataSource.insertSeries( listSeries )
            }

        }.asLiveData()
    }

    override fun getDetailMovieFromApi(movieId: Int): LiveData<Resource<MovieDetail>> {
        return mRemoteDataSource.getDetailMovie(movieId)
    }

    override fun getDetailSeriesFromApi(seriesId: Int): LiveData<Resource<SeriesDetail>> {
        return  mRemoteDataSource.getDetailSeries(seriesId)
    }

    override fun getFavorites(sort: String): LiveData<PagedList<FavoriteEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        val query = SortUtils.getSortedQuery(sort)
        return LivePagedListBuilder(mLocalDataSource.getAllFavorite(query), config).build()
    }

    override fun setFavorite(fav: FavoriteEntity) {
        mExecutor.diskIO().execute { mLocalDataSource.insertFavorite(fav) }
    }

    override fun getFavoriteById(id: Int, type: String): LiveData<FavoriteEntity> {
        val data = MutableLiveData<FavoriteEntity>()
        mExecutor.diskIO().execute {
            data.postValue(mLocalDataSource.getFavoriteByIdAndType(id, type))
        }
        return data
    }

    override fun deleteFavorite(id: Int, type: String) {
        mExecutor.diskIO().execute { mLocalDataSource.deleteFavorite(id, type) }
    }

}