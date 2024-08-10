package com.renhard.contactme.module.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.renhard.contactme.R
import com.renhard.contactme.databinding.ContactItemViewBinding
import com.renhard.contactme.module.main.model.ContactItemModel
import com.renhard.contactme.module.maps.view.LocationMapsActivity
import com.renhard.contactme.utils.constant.Constant

class MainAdapter : PagingDataAdapter<ContactItemModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    private val VIEW_ITEM = 0

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContactItemModel>() {
            override fun areItemsTheSame(oldItem: ContactItemModel, newItem: ContactItemModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ContactItemModel, newItem: ContactItemModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemHomeBinding = ContactItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ItemViewHolder(itemHomeBinding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder = holder as ItemViewHolder
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_ITEM
    }

    override fun getItemCount(): Int {
        return snapshot().items.size
    }

    class ItemViewHolder(private val binding: ContactItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactItemModel) {
            with(binding) {
                val context = tvId.context
                tvId.text = "${item.id}"
                tvFullName.text = item.name
                tvUserName.text = item.userName
                tvEmail.text = item.email
                tvAddress.text = "${item.address.street} St, ${item.address.suite}, ${item.address.city}, ${item.address.zipcode}"

                Glide.with(context)
                    .load(item.thumbnail)
                    .circleCrop()
                    .into(ivThumbnail)

                interactionAddress.setOnClickListener {
                    val locationMapsIntent = Intent(context, LocationMapsActivity::class.java)
                    locationMapsIntent.putExtra("address", item.address)
                    context.startActivity(locationMapsIntent)
                }
            }
        }
    }
}