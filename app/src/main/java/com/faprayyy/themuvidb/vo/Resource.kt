package com.faprayyy.themuvidb.vo

class Resource<T>(val status: StatusMessage, val data: T?, val message: String?){
    companion object{
        fun <T> success(data: T?): Resource<T> = Resource(StatusMessage.SUCCESS, data, null)

        fun <T> error(msg: String?, data: T?): Resource<T> = Resource(StatusMessage.ERROR, data, msg)

        fun <T> loading(data: T?): Resource<T> = Resource(StatusMessage.LOADING, data, null)

    }
}