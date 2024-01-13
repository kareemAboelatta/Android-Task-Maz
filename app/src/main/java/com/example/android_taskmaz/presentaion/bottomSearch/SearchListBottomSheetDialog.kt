package com.example.android_taskmaz.presentaion.bottomSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.example.android_taskmaz.R
import com.example.android_taskmaz.common.utils.getParcelableArrayList
import com.example.android_taskmaz.common.utils.setUp
import com.example.android_taskmaz.databinding.DialogBottomSearchListBinding
import com.example.android_taskmaz.presentaion.adapters.BottomSearchListAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class SearchListBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: DialogBottomSearchListBinding? = null
    private val binding get() = _binding!!

    private val searchAdapter: BottomSearchListAdapter by lazy {
        BottomSearchListAdapter(arguments?.getBoolean(ARG_HAS_OTHER_VALUE) ?: false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogBottomSearchListBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        binding.apply {
            titleTextView.text = arguments?.getString(ARG_TITLE, getString(R.string.select))
            closeImageView.setOnClickListener { dismiss() }
            searchEditText.addTextChangedListener {
                searchAdapter.filter.filter(it.toString())
            }

            recyclerView.setUp(searchAdapter, isDivider = true)
            searchAdapter.onItemClick = ::onItemSelected
            val items = getParcelableArrayList(ARG_LIST_ITEMS, SearchItem::class.java) ?: emptyList()
            searchAdapter.submitList(items)
            searchAdapter.originalList = items
        }
    }

    var onSearchItemSelected: (SearchItem) -> Unit = {}

    private fun onItemSelected(item: SearchItem) {
        onSearchItemSelected(item)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_HAS_OTHER_VALUE = "hasOtherValue"
        private const val ARG_LIST_ITEMS = "listItems"

        fun newInstance(
            title: String? = null,
            hasOtherValue: Boolean = false,
            listItems: ArrayList<SearchItem>
        ) = SearchListBottomSheetDialog().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
                putBoolean(ARG_HAS_OTHER_VALUE, hasOtherValue)
                putParcelableArrayList(ARG_LIST_ITEMS, listItems)
            }
        }
    }


}
