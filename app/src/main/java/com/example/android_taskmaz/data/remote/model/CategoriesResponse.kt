package com.example.android_taskmaz.data.remote.model

import com.example.android_taskmaz.domain.models.Category
import com.example.android_taskmaz.domain.models.SubCategory


data class CategoriesData(
    val categories: List<ApiCategoryModel>,
    val statistics: Statistics,
    val ads_banners: List<AdsBanner>,
    val ios_version: String,
    val ios_latest_version: String,
    val google_version: String,
    val huawei_version: String
)

data class ApiCategoryModel(
    val id: Int,
    val name: String,
    val description: String?,
    val image: String,
    val slug: String,
    val children: List<ApiCategoryModel>?,
    val circle_icon: String?,
    val disable_shipping: Int
)

data class Statistics(
    val auctions: Int,
    val users: Int,
    val products: Int
)

data class AdsBanner(
    val img: String,
    val media_type: String,
    val duration: Int
)



//Todo separate as a mapper file
fun ApiCategoryModel.toCategory(): Category {
    return Category(
            id = id,
            name = name,
            options = children?.map { subCategory ->
                SubCategory(
                    id = subCategory.id,
                    name = subCategory.name
                )
            } ?: listOf()
        )

}



