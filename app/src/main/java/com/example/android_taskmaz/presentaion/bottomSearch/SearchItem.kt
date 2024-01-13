package com.example.android_taskmaz.presentaion.bottomSearch

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchItem(
    val id: Int,
    val name: String?,
): Parcelable
