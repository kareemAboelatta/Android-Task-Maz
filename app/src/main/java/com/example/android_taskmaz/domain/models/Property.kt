package com.example.android_taskmaz.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Property(
    val id: Int,
    val name: String,
    val options: List<Option>
): Parcelable

