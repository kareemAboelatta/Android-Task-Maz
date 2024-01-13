package com.example.android_taskmaz.presentaion.fragments.select_product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.android_taskmaz.R
import com.example.android_taskmaz.common.ui.ProgressDialogUtil
import com.example.android_taskmaz.common.utils.UIState
import com.example.android_taskmaz.common.utils.setUp
import com.example.android_taskmaz.common.utils.showSnackMsg
import com.example.android_taskmaz.databinding.FragmentSelectProductBinding
import com.example.android_taskmaz.domain.models.Property
import com.example.android_taskmaz.presentaion.adapters.PropertyOptionsAdapter
import com.example.android_taskmaz.presentaion.bottomSearch.SearchItem
import com.example.android_taskmaz.presentaion.bottomSearch.SearchListBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MainFragment"

@AndroidEntryPoint
class SelectProductFragment : Fragment() {

    private var _binding: FragmentSelectProductBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding not initialized")

    private val viewModel by viewModels<SelectProductViewModel>()
    private lateinit var propertyOptionsAdapter: PropertyOptionsAdapter


    private var progressDialogUtil  =  ProgressDialogUtil()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUi()
        observeViewModel()
    }

    private fun submitRequest() {
        SelectedValuesDialog.newInstance(viewModel.requestState.value)
            .show(childFragmentManager, "")
    }


    private fun initializeUi() {
        propertyOptionsAdapter = PropertyOptionsAdapter(::onPropertyClick, ::onUserInputChanged)
        binding.rvProperty.setUp(propertyOptionsAdapter)
        setupClickListeners()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.requestState.collectLatest { request ->
                binding.etlCategoryBtn.setText(request.selectedCategory?.name)
                binding.etlSubcategoryBtn.setText(request.selectedSubCategory?.name)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.propertiesState.collect { state ->
                handlePropertiesState(state)
            }
        }
    }

    private fun handlePropertiesState(state: UIState<List<Property>>) {
        when (state) {
            UIState.Empty -> Log.d(TAG, "Properties state is empty")
            is UIState.Error -> {
                Toast.makeText(context, "Error loading properties: ${state.error}", Toast.LENGTH_SHORT).show()
                progressDialogUtil.hideProgressDialog()
            }
            UIState.Loading -> {
                context?.let { progressDialogUtil.showProgressDialog(it) }
            }
            is UIState.Success -> {
                propertyOptionsAdapter.submitList(state.data)
                progressDialogUtil.hideProgressDialog()
            }
        }
    }

    private fun setupClickListeners() {
        binding.etlCategoryBtn.setOnClickListener { chooseCategory() }
        binding.etlSubcategoryBtn.setOnClickListener { chooseSubCategory() }
        binding.btnSubmit.setOnClickListener { submitRequest() }
    }


    private fun onPropertyClick(index: Int, item: Property) {
        val list = item.options.map { SearchItem(it.id, it.name) }

        showSearchDialog(
            hasOtherValue = true,
            title = item.name,
            list = ArrayList<SearchItem>(list.size).apply { addAll(list) }
        ) { searchItem ->
            if (searchItem.id == -1) { // other clicked
                propertyOptionsAdapter.toggleUserInputVisibility(index = index, isVisible = true)
                propertyOptionsAdapter.setSelectedOption(index, getString(R.string.other))
            } else {
                propertyOptionsAdapter.toggleUserInputVisibility(index = index, isVisible = false)
                viewModel.setSelectedPropertyOption(item, searchItem.id, searchItem.name)
                propertyOptionsAdapter.setSelectedOption(
                    index,
                    searchItem.name ?: ""
                )
            }
        }
    }


    private fun onUserInputChanged(property: Property, userInputText: String?) {
        viewModel.setSelectedPropertyOption(property, -1, userInputText)
    }


    private fun showSearchDialog(
        hasOtherValue: Boolean = false,
        title: String?,
        list: ArrayList<SearchItem>,
        onSearchItemSelected: (SearchItem) -> Unit,
    ) {
        SearchListBottomSheetDialog.newInstance(
            hasOtherValue = hasOtherValue, title = title,
            listItems = list
        ).apply {
            this.onSearchItemSelected = onSearchItemSelected
        }.show(childFragmentManager, "")
    }


    private fun chooseCategory() {
        val categories = (viewModel.categoriesState.value as? UIState.Success)?.data?.map {
            SearchItem(it.id, it.name)
        }
        if (categories.isNullOrEmpty()) {
            binding.root.showSnackMsg(R.string.wait_for_categories)
            return
        }

        showSearchDialog(title = getString(R.string.main_category),
            list = ArrayList<SearchItem>(categories.size).apply { addAll(categories) })
        { searchItem ->
            Log.d(TAG, "chooseCategory: ===($searchItem)")
            viewModel.setSelectedCategory(searchItem.id)
            propertyOptionsAdapter.clearSelectedOptions()


        }
    }


    private fun chooseSubCategory() {
        if (viewModel.requestState.value.selectedCategory == null) {
            binding.root.showSnackMsg(R.string.no_category_was_selected)
            return
        }

        viewModel.getSubCategories(viewModel.requestState.value.selectedCategory!!.id) {
            val list = it?.map { subCategory ->
                SearchItem(subCategory.id, subCategory.name)
            }

            showSearchDialog(
                title = getString(R.string.sub_category),
                list = ArrayList<SearchItem>(list!!.size).apply { addAll(list) })
            { searchItem ->
                viewModel.setSelectedSubCategory(searchItem.id)
                propertyOptionsAdapter.clearSelectedOptions()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}