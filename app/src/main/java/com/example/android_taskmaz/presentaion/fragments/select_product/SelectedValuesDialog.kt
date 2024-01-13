package com.example.android_taskmaz.presentaion.fragments.select_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.android_taskmaz.R
import com.example.android_taskmaz.common.utils.getParcelable
import com.example.android_taskmaz.common.utils.setUp
import com.example.android_taskmaz.databinding.DialogSelectedValuesBinding
import com.example.android_taskmaz.domain.models.requests.ProductRequest
import com.example.android_taskmaz.presentaion.adapters.SelectedPropertiesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SelectedValuesDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogSelectedValuesBinding
    private lateinit var adapter: SelectedPropertiesAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = DialogSelectedValuesBinding.inflate(inflater, container, false)
        setUp()
        return binding.root
    }

    private fun setUp() {
        adapter = SelectedPropertiesAdapter()

        val request = getParcelable(ARGS_REQUEST, ProductRequest::class.java)
        binding.apply {
            rv.setUp(adapter, isDivider = true)
            tvMainCat.text = request?.selectedCategory?.name
            tvSubCat.text = request?.selectedSubCategory?.name
        }
        adapter.submitList(request?.propertyOptions?.values?.reversed())

        onClicks()
    }

    private fun onClicks() {
        binding.btnSubmit.setOnClickListener {
            dismiss()
        }

    }

    companion object {
        private const val ARGS_REQUEST = "ARGS_REQUEST"

        fun newInstance(
            request: ProductRequest,
        ) =
            SelectedValuesDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARGS_REQUEST, request)
                }
            }
    }


}