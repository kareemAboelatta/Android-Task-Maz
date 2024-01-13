package com.example.android_taskmaz.data.remote.model.common

import com.example.android_taskmaz.domain.models.Option
import com.google.gson.annotations.SerializedName

data class ApiOptionModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("parent") val parent: Int,
    @SerializedName("child") val child: Boolean
)



//Todo separate as a mapper file
fun ApiOptionModel.toOption(): Option {
    return  Option(
        id =  id,
        name =  name
    )
}
