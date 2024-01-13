package com.example.android_taskmaz.data.remote.model

import com.example.android_taskmaz.data.remote.model.common.ApiOptionModel
import com.example.android_taskmaz.data.remote.model.common.toOption
import com.example.android_taskmaz.domain.models.OptionGroup
import com.example.android_taskmaz.domain.models.Property
import com.google.gson.annotations.SerializedName

data class ApiOptionGroupModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("slug") val slug: String,
    @SerializedName("parent") val parent: Int?,
    @SerializedName("list") val list: Boolean,
    @SerializedName("type") val type: String?,
    @SerializedName("value") val value: String?,
    @SerializedName("other_value") val otherValue: Any?,
    @SerializedName("options") val apiOptionModels: List<ApiOptionModel>
)


//Todo separate as a mapper file
fun ApiOptionGroupModel.toPropertyList(): Property {
    return Property(
            id = id,
            name = name,
            options = apiOptionModels.map { apiOption ->
                apiOption.toOption()
            }
        )
}




