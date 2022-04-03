package com.faprayyy.themuvidb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.faprayyy.themuvidb.utils.AppExecutors
import com.faprayyy.themuvidb.vo.Resource
import com.faprayyy.themuvidb.vo.StatusMessage

abstract class NetworkBoundResource<ResultType, RequestType>(private val executors: AppExecutors) {

    private val resultData = MediatorLiveData<Resource<ResultType>>()

    init {
        resultData.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        resultData.addSource(dbSource){ data ->
            resultData.removeSource( dbSource )

            if (shouldFecth(data)){
                fetchFromNetwork(dbSource)
            }else{
                resultData.addSource( dbSource ){
                    resultData.value = Resource.success( it )
                }
            }
        }
    }

    protected fun onFetchFailed(){}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFecth(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<Resource<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    protected fun fetchFromNetwork(dbSource: LiveData<ResultType>){
        val response = createCall()

        resultData.addSource(dbSource){ newData ->
            resultData.value = Resource.loading(newData)
        }

        resultData.addSource(response){ data ->
            resultData.removeSource(response)
            resultData.removeSource(dbSource)
            when(data.status){
                StatusMessage.SUCCESS ->
                    executors.diskIO().execute {
                        saveCallResult( data.data!! )
                        executors.mainThread().execute {
                            resultData.addSource(loadFromDB()){ newData->
                                resultData.value = Resource.success( newData )
                            }
                        }
                    }

                StatusMessage.EMPTY ->
                    executors.mainThread().execute {
                        resultData.addSource(loadFromDB()){ newData ->
                            resultData.value = Resource.success(newData)
                        }
                    }

                StatusMessage.ERROR -> {
                    onFetchFailed()
                    resultData.addSource(dbSource){ newData ->
                        resultData.value = Resource.error(data.message, newData)
                    }
                }
                else -> { }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = resultData
}