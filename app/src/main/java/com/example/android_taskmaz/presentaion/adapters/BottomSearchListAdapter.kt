package com.example.android_taskmaz.presentaion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_taskmaz.R
import com.example.android_taskmaz.presentaion.bottomSearch.SearchItem
import com.example.android_taskmaz.databinding.ItemSearchListBinding

/**
 * Adapter for displaying search items.
 * If 'hasOtherValue' is true, an extra 'Other' item is added at the start of the list.
 */
class BottomSearchListAdapter(
    val hasOtherValue: Boolean,
) :
    ListAdapter<SearchItem, BottomSearchListAdapter.ViewHolder>(DiffCallback()),
    Filterable {

    var onItemClick: (SearchItem) -> Unit = {}
    var originalList: List<SearchItem> = arrayListOf()

    /* the count is plus one because there is always value called 'other' in the adapter. */
    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasOtherValue) 1 else 0
    }

    override fun getItem(position: Int): SearchItem {
        return super.getItem(position - if (hasOtherValue) 1 else 0)
    }

    override fun getItemViewType(position: Int): Int {
        return position - if (hasOtherValue) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(
            if (hasOtherValue && position == 0)
                SearchItem(-1, holder.binding.root.context.getString(R.string.other))
            else

                getItem(position)
        )
    }

    inner class ViewHolder(val binding: ItemSearchListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(
                    if (hasOtherValue && adapterPosition == 0)
                        SearchItem(id = -1, name = null)
                    else
                        getItem(adapterPosition)
                )
            }
        }

        fun bind(item: SearchItem) = with(binding) {
            binding.item = item
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                return FilterResults().apply {
                    values = if (constraint.isNullOrEmpty())
                        originalList
                    else
                        filterList(originalList, constraint.toString())

                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                val data = results?.values as? List<SearchItem>
                submitList(data?.toList())

            }

        }
    }

    private fun filterList(list: List<SearchItem>, query: String): List<SearchItem> {
        return list.filter {
            it.name?.lowercase()?.contains(query.lowercase()) == true
        }
    }
}


