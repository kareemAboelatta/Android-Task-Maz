package com.example.android_taskmaz.common.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException


suspend fun <T : Any, R : Any> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T,
    transform: (T) -> R
): Resource<R> = withContext(dispatcher) {
    try {
        val response = apiCall.invoke()
        Resource.Success(transform(response))
    } catch (throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                Resource.Failure(
                    FailureStatus.API_FAIL,
                    throwable.code(),
                    throwable.response()?.errorBody()?.string()
                )
            }
            is UnknownHostException, is ConnectException -> {
                Resource.Failure(FailureStatus.NO_INTERNET)
            }
            is IOException -> {
                Resource.Failure(FailureStatus.API_FAIL)
            }
            else -> {
                Resource.Failure(FailureStatus.OTHER)
            }
        }
    }
}

