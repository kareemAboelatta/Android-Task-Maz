package com.example.android_taskmaz.presentaion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_taskmaz.R
import com.example.android_taskmaz.databinding.ItemPropertyBinding
import com.example.android_taskmaz.domain.models.Property

class PropertyOptionsAdapter(
    private val onPropertyClick: (Int, Property) -> Unit,
    private val onUserInputChanged: (Property, String?) -> Unit
) : ListAdapter<Property, PropertyOptionsAdapter.ViewHolder>(PropertyDiffCallback()) {

    private val selectedOptions = HashMap<Int, String>()
    private val userInputVisibility = HashSet<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemPropertyBinding>(
            LayoutInflater.from(parent.context), R.layout.item_property, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setSelectedOption(index: Int, optionName: String) {
        selectedOptions[index] = optionName
        notifyItemChanged(index)
    }

    fun clearSelectedOptions() {
        selectedOptions.clear()
        userInputVisibility.clear()
    }

    fun toggleUserInputVisibility(index: Int, isVisible: Boolean) {
        if (isVisible) userInputVisibility.add(index) else userInputVisibility.remove(index)
        notifyItemChanged(index)
    }

    inner class ViewHolder(private val binding: ItemPropertyBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.etlProp.editText?.setOnClickListener {
                onPropertyClick(adapterPosition, getItem(adapterPosition))
            }
            binding.etlInputValue.editText?.addTextChangedListener {
                onUserInputChanged(getItem(adapterPosition), it?.toString())
            }
        }

        fun bind(item: Property) {
            binding.apply {
                this.item = item
                selectedOption = selectedOptions[adapterPosition]
                isUserInputShown = userInputVisibility.contains(adapterPosition)
            }
        }
    }

    class PropertyDiffCallback : DiffUtil.ItemCallback<Property>() {
        override fun areItemsTheSame(oldItem: Property, newItem: Property) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Property, newItem: Property) = oldItem == newItem
    }
}
