package com.example.android_taskmaz.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Option(
    val id: Int,
    val name: String
): Parcelable