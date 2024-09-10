package com.aetherinsight.goldentomatoes.core.network.model

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val message: String?) : Resource<T>()
    class Loading<T> : Resource<T>()
}

fun <T, R> Resource<T>.mapTransform(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(message)
        is Resource.Loading -> Resource.Loading()
    }
}

fun <T, R> Resource<List<T>>.mapList(transform: (T) -> R): Resource<List<R>> {
    return when (this) {
        is Resource.Success -> Resource.Success(data.map(transform))
        is Resource.Error -> Resource.Error(message)
        is Resource.Loading -> Resource.Loading()
    }
}
