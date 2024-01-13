package com.example.android_taskmaz.data.remote.model.common


/**
 * T is data
 * */
class MazaadyResponse<T>(
    var msg: String ,
    var code: Int ,
    val data: T
)

