package com.example.android_taskmaz.domain.usecases

import com.example.android_taskmaz.common.utils.Resource
import com.example.android_taskmaz.domain.models.Property
import com.example.android_taskmaz.domain.repository.Repository
import javax.inject.Inject




class GetPropertiesUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(categoryId: Int): Resource<List<Property>> = repository.getProperties(categoryId)
}
