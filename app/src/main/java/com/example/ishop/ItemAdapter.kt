package com.example.ishop

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ishop.database.GroceryItem
import com.example.ishop.databinding.ListItemGroceryItemBinding
import com.example.ishop.databinding.ListItemNewListBinding


//ViewHolder for Shopping mode
class ItemAdapterShopping : ListAdapter<GroceryItem,ItemAdapterShopping.ViewHolder>(GroceryItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.itemName.text = item.Item

        if (!holder.binding.gotItemSwitch.isChecked) {
            holder.binding.itemName.paintFlags = 0
        }
        holder.binding.gotItemSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.binding.itemName.paintFlags = STRIKE_THRU_TEXT_FLAG
            } else {
                holder.binding.itemName.paintFlags = 0
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemGroceryItemBinding.inflate(layoutInflater,parent, false)
        return ViewHolder(binding)
    }
    class ViewHolder(val binding: ListItemGroceryItemBinding): RecyclerView.ViewHolder(binding.root)
}

//ViewHolder for NewList
class ItemAdapterNewList : ListAdapter<GroceryItem,ItemAdapterNewList.ViewHolder>(GroceryItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.itemName.text = item.Item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemNewListBinding.inflate(layoutInflater,parent, false)
        return ViewHolder(binding)
    }
    class ViewHolder(val binding: ListItemNewListBinding): RecyclerView.ViewHolder(binding.root)
}

class GroceryItemDiffCallback : DiffUtil.ItemCallback<GroceryItem>() {
    override fun areItemsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
        return oldItem.ItemId == newItem.ItemId
    }

    override fun areContentsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
        return oldItem == newItem
    }
}

//ViewHolder for Manage Lists
class ItemAdapterStrings : ListAdapter<String,ItemAdapterStrings.ViewHolder>(StringDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.itemName.text = item
    }

    fun getItemByPos(position: Int): String {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemNewListBinding.inflate(layoutInflater,parent, false)
        return ViewHolder(binding)
    }
    class ViewHolder(val binding: ListItemNewListBinding): RecyclerView.ViewHolder(binding.root)
}

class StringDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}