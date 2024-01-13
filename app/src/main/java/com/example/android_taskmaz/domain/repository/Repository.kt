package com.example.android_taskmaz.domain.repository


import com.example.android_taskmaz.common.utils.Resource
import com.example.android_taskmaz.domain.models.Category
import com.example.android_taskmaz.domain.models.OptionGroup
import com.example.android_taskmaz.domain.models.Property

interface Repository {
    suspend fun getCategories(): Resource<List<Category>>
    suspend fun getProperties(categoryId: Int): Resource<List<Property>>
    suspend fun getOptions(groupId: Int): Resource<List<Property>>
}
