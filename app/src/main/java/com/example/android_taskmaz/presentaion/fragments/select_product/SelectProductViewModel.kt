package com.example.android_taskmaz.presentaion.fragments.select_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_taskmaz.common.utils.Resource
import com.example.android_taskmaz.common.utils.UIState
import com.example.android_taskmaz.domain.models.Category
import com.example.android_taskmaz.domain.models.Property
import com.example.android_taskmaz.domain.models.SubCategory
import com.example.android_taskmaz.domain.models.requests.ProductRequest
import com.example.android_taskmaz.domain.usecases.GetCategoriesUseCase
import com.example.android_taskmaz.domain.usecases.GetOptionsUseCase
import com.example.android_taskmaz.domain.usecases.GetPropertiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * SelectProductViewModel responsible for managing the state of selecting a product, including categories,
 * properties, and subcategories.
 */
@HiltViewModel
class SelectProductViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getPropertiesUseCase: GetPropertiesUseCase,
    private val getOptionsUseCase: GetOptionsUseCase,
) : ViewModel() {

    private val _categoriesState = MutableStateFlow<UIState<List<Category>>>(UIState.Empty)
    val categoriesState = _categoriesState.asStateFlow()

    private val _propertiesState = MutableStateFlow<UIState<List<Property>>>(UIState.Empty)
    val propertiesState = _propertiesState.asStateFlow()

    private val _requestState = MutableStateFlow(ProductRequest())
    val requestState: StateFlow<ProductRequest> get() = _requestState


    private var propertiesJob: Job? = null
    private var optionsJob: Job? = null


    init {
        loadCategories()
    }

    private fun loadCategories() {
        _categoriesState.value = UIState.Loading
        viewModelScope.launch {
            when (val result = getCategoriesUseCase()) {
                is Resource.Failure -> _categoriesState.value = UIState.Error(result.status.name)
                is Resource.Success -> _categoriesState.value = UIState.Success(result.data)
            }
        }
    }


    /**
     * Retrieves subcategories for a given category ID.
     * @param CategoryId The ID of the category.
     * @param onSubCategoriesFound Callback function to handle the found subcategories.
     */
    fun getSubCategories(CategoryId: Int, onSubCategoriesFound: (List<SubCategory>?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val subCategories = (_categoriesState.value as? UIState.Success)
                ?.data
                ?.find { it.id == CategoryId }
                ?.options
            onSubCategoriesFound(subCategories)
        }
    }



    private fun getProperties(subCatId: Int) {
        _propertiesState.value = UIState.Loading

        viewModelScope.launch {

            propertiesJob?.cancel()
            propertiesJob = launch {
                delay(3000) // simulate loading

                when (val properties = getPropertiesUseCase(subCatId)) {
                    is Resource.Failure -> {
                        _propertiesState.value = UIState.Error(properties.status.name)
                    }
                    is Resource.Success -> {
                        _propertiesState.value = UIState.Success(properties.data)
                    }
                }
            }
        }
    }


    fun setSelectedCategory(catId: Int) {
        viewModelScope.launch{
            /**
             * .update{} better than _requestState.value = requestState.value.copy(selectedSubCategory = null) for race condition
             * */
            _requestState.update {
                it.copy(
                    selectedCategory = (_categoriesState.value as? UIState.Success)?.data?.find { it.id == catId },
                    selectedSubCategory = null
                )
            }
            clearProperties()
        }
    }

    fun setSelectedSubCategory(subCatId: Int) {
        clearProperties()
        getProperties(subCatId)
        viewModelScope.launch {
            _requestState.update {
                it.copy(
                    selectedSubCategory = (_categoriesState.value as? UIState.Success)?.data?.find {
                        it.id == requestState.value.selectedCategory?.id
                    }?.options?.find { it.id == subCatId }
                )
            }
        }
    }


    private fun clearProperties() {
        viewModelScope.launch {
            _requestState.value = requestState.value.copy( )
            _requestState.update {
                it.copy(
                    propertyOptions =  hashMapOf()
                )
            }
            _propertiesState.emit(UIState.Success(emptyList()))
        }
    }


    fun setSelectedPropertyOption(
        property: Property,
        selectedOptionId: Int,
        optionName: String? = null,
    ) {
        val propertyOptionItem = ProductRequest.PropertyOption(
            property,
            selectedOptionId,
            optionName
        )
        _requestState.value.propertyOptions[property.id] = propertyOptionItem
        _requestState.value = requestState.value

        if (selectedOptionId != -1) {
            getPropertiesByOptions(property.id, selectedOptionId)
        }
    }


    private fun getPropertiesByOptions(propertyId: Int?, optionId: Int) {
        viewModelScope.launch {
            optionsJob?.cancel()
            optionsJob = launch {
                val optionsResult = getOptionsUseCase(optionId)
                if (optionsResult is Resource.Success) {
                    val newData = optionsResult.data
                    if (newData.isNotEmpty()) {
                        val oldList = (_propertiesState.value as? UIState.Success)?.data.orEmpty()
                        val updatedList = insertNewDataIntoPropertiesList(oldList, newData, propertyId)
                        _propertiesState.value = UIState.Success(updatedList)
                    }
                }
            }
        }
    }



    /**
     * Inserts new data into the properties list at the correct position.
     * @param oldList The original list of properties.
     * @param newData The new data to be inserted.
     * @param propertyId The ID of the property to which the new data belongs.
     * @return A list containing the old data and the new data inserted at the correct position.
     */
    private fun insertNewDataIntoPropertiesList(
        oldList: List<Property>,
        newData: List<Property>,
        propertyId: Int?
    ): List<Property> {
        val indexOfParentProperty = oldList.indexOfFirst { it.id == propertyId }
            .takeIf { it >= 0 } ?: oldList.size

        return oldList.toMutableList().apply {
            addAll(indexOfParentProperty, newData)
        }
    }

}
