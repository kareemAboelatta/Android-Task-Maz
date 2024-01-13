package com.example.android_taskmaz.common.utils

import androidx.annotation.StringRes

sealed class Error {
    class ErrorStr(val msg: String) : Error()
    class ErrorInt(@StringRes val msg: Int) : Error()
}
