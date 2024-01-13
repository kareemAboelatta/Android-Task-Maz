package com.example.android_taskmaz.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val options: List<SubCategory>
): Parcelable


@Parcelize
data class SubCategory(
    val id: Int,
    val name: String
) : Parcelable



