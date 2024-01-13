package com.example.android_taskmaz.domain.usecases


import com.example.android_taskmaz.common.utils.Resource
import com.example.android_taskmaz.domain.models.Category
import com.example.android_taskmaz.domain.repository.Repository

import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): Resource<List<Category>> = repository.getCategories()
}


