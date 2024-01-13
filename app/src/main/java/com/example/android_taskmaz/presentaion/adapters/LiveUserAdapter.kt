package com.example.android_taskmaz.presentaion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_taskmaz.databinding.ItemLiveUserBinding
import com.example.android_taskmaz.domain.models.LiveUser

class LiveUserAdapter(private val userList: List<LiveUser>) : RecyclerView.Adapter<LiveUserAdapter.LiveUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveUserViewHolder {
        val binding = ItemLiveUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LiveUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LiveUserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount() = userList.size

    class LiveUserViewHolder(private val binding: ItemLiveUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: LiveUser) {

            Glide.with(itemView).load(user.imageUrl).into(binding.liveUserImage)
            binding.imgOnlineStatus.visibility = if (user.isOnline) View.VISIBLE else View.GONE
        }
    }
}
