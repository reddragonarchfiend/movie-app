package com.example.movieapp.util

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        UNAUTHORIZED
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, null)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

        fun <T> unauthorized(): Resource<T> {
            return Resource(Status.UNAUTHORIZED, null, null)
        }
    }
}