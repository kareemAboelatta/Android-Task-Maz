package com.example.android_taskmaz.data.repository


import com.example.android_taskmaz.common.utils.Resource
import com.example.android_taskmaz.common.utils.safeApiCall
import com.example.android_taskmaz.data.remote.ApiService
import com.example.android_taskmaz.data.remote.model.toCategory
import com.example.android_taskmaz.data.remote.model.toPropertyList
import com.example.android_taskmaz.data.remote.model.toProperty
import com.example.android_taskmaz.domain.models.Category
import com.example.android_taskmaz.domain.models.OptionGroup
import com.example.android_taskmaz.domain.models.Property
import com.example.android_taskmaz.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher



class RepositoryImpl(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) : Repository {

    override suspend fun getCategories(): Resource<List<Category>> = safeApiCall(
        apiCall = { apiService.getCategories() },
        transform = { response -> response.data.categories.map { it.toCategory() } },
        dispatcher = ioDispatcher
    )

    override suspend fun getProperties(categoryId: Int): Resource<List<Property>> = safeApiCall(
        apiCall = { apiService.getProperties(categoryId) },
        transform = { response -> response.data.map { it.toProperty() } },
        dispatcher = ioDispatcher
    )

    override suspend fun getOptions(groupId: Int): Resource<List<Property>> = safeApiCall(
        apiCall = { apiService.getOptions(groupId) },
        transform = { response -> response.data.map { it.toPropertyList() } },
        dispatcher = ioDispatcher
    )
}



