package com.example.android_taskmaz.presentaion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_taskmaz.R
import com.example.android_taskmaz.databinding.ItemSelectedValuesPropertyBinding
import com.example.android_taskmaz.domain.models.requests.ProductRequest


class SelectedPropertiesAdapter(
) : ListAdapter<ProductRequest.PropertyOption, SelectedPropertiesAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_selected_values_property,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ItemSelectedValuesPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductRequest.PropertyOption) = with(binding) {
            binding.item = item
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ProductRequest.PropertyOption>() {
        override fun areItemsTheSame(oldItem: ProductRequest.PropertyOption, newItem: ProductRequest.PropertyOption): Boolean {
            return oldItem.property.id == newItem.property.id
        }

        override fun areContentsTheSame(oldItem: ProductRequest.PropertyOption, newItem: ProductRequest.PropertyOption): Boolean {
            return oldItem == newItem
        }
    }
}
