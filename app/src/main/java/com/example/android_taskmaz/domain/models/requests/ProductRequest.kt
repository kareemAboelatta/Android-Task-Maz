package com.example.android_taskmaz.domain.models.requests

import android.os.Parcelable
import com.example.android_taskmaz.domain.models.Category
import com.example.android_taskmaz.domain.models.Property
import com.example.android_taskmaz.domain.models.SubCategory
import kotlinx.parcelize.Parcelize

/**
 * Represents a request for a main product, including selected category, subcategory,
 * and custom properties.
 */
@Parcelize
data class ProductRequest(
    var selectedCategory: Category? = null,
    var selectedSubCategory: SubCategory? = null,
    // Storing property options as a map for easy access by property ID.
    var propertyOptions: HashMap <Int, PropertyOption> = hashMapOf(),
): Parcelable {

    @Parcelize
    data class PropertyOption(
        val property: Property,
        val optionId: Int? = null,
        val customOptionName: String? = null,
    ): Parcelable
}
