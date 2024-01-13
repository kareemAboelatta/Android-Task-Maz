package com.example.android_taskmaz.common.utils


sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(
        val status: FailureStatus,
        val errorCode: Int? = null,
        val errorMessage: String? = null
    ) : Resource<Nothing>()
}


sealed class UIState<out T> {
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val error: String) : UIState<Nothing>()
    data object Loading : UIState<Nothing>()
    data object Empty : UIState<Nothing>()
}



enum class FailureStatus {
    API_FAIL,
    UNAUTHENTICATED,
    NO_INTERNET,
    OTHER
}