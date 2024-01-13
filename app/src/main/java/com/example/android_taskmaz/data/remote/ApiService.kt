package com.example.android_taskmaz.data.remote

import com.example.android_taskmaz.data.remote.model.ApiOptionGroupModel
import com.example.android_taskmaz.data.remote.model.ApiPropertyModel
import com.example.android_taskmaz.data.remote.model.CategoriesData
import com.example.android_taskmaz.data.remote.model.common.MazaadyResponse

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("api/v1/get_all_cats")
    suspend fun getCategories(): MazaadyResponse<CategoriesData>

    @GET("api/v1/properties")
    suspend fun getProperties(@Query("cat") id: Int): MazaadyResponse<List<ApiPropertyModel>>

    @GET("api/v1/get-options-child/{id}")
    suspend fun getOptions(@Path("id") id: Int): MazaadyResponse<List<ApiOptionGroupModel>>




}
