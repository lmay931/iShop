package com.example.ishop

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ishop.database.GroceryItem
import com.example.ishop.database.GroceryItemListDatabaseDao
import com.example.ishop.databinding.ListItemAddItemsBinding
import com.example.ishop.databinding.ListItemGroceryItemBinding
import com.example.ishop.databinding.ListItemNewListBinding
import com.example.ishop.databinding.ListItemShoppingWithCategoriesBinding


//ViewHolder for Shopping mode
class ItemAdapterShopping(
    private val viewModel: ShoppingViewModel,
    private val viewLifecycleOwner: LifecycleOwner,
    private val database: GroceryItemListDatabaseDao
) : ListAdapter<String,ItemAdapterShopping.ViewHolder>(StringDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.headerCategory.text = item

        val adapter = ItemAdapterNewShoppingList(viewModel)
        holder.binding.addedItemsRecyclerview.adapter = adapter

        val addedItemList = database.get(viewModel.listName, getItem(position))
        addedItemList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemShoppingWithCategoriesBinding.inflate(layoutInflater,parent, false)
        return ViewHolder(binding)
    }
    class ViewHolder(val binding: ListItemShoppingWithCategoriesBinding): RecyclerView.ViewHolder(binding.root)
}

//ItemAdapter for AddItems
class ItemAdapterAddItems(
    private val viewModel: AddItemsViewModel,
    private val viewLifecycleOwner: LifecycleOwner,
    private val database: GroceryItemListDatabaseDao
) : ListAdapter<String,ItemAdapterAddItems.ViewHolder>(StringDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.headerNewCategory.text = item

        val adapter = ItemAdapterNewList()
        holder.binding.addedItemsRecyclerview.adapter = adapter
        holder.binding.addNewItem.hint = "Add new " + getItem(position) + " item"

        holder.binding.buttonAddNewItem.setOnClickListener {
            if(holder.binding.addNewItem.text.toString()==""){ viewModel.setSnackBar()}
            else{
                viewModel.addItem(getItem(position),holder.binding.addNewItem.text.toString())
                holder.binding.addNewItem.text.clear()
            }
        }

        val addedItemList = database.get(viewModel.listName, getItem(position))
        addedItemList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.remove(adapter.get(viewHolder.adapterPosition))
                }
            })

        itemTouchHelper.attachToRecyclerView(holder.binding.addedItemsRecyclerview)
    }

    fun getItemByPos(position: Int): String {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemAddItemsBinding.inflate(layoutInflater, parent, false)


        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ListItemAddItemsBinding) : RecyclerView.ViewHolder(binding.root)
}

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

    fun get(adapterPosition: Int): Long {
        return getItem(adapterPosition).ItemId
    }

    class ViewHolder(val binding: ListItemNewListBinding): RecyclerView.ViewHolder(binding.root)
}

class ItemAdapterNewShoppingList(private val viewModel: ShoppingViewModel) : ListAdapter<GroceryItem,ItemAdapterNewShoppingList.ViewHolder>(GroceryItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.itemName.text = item.Item

        if (!item.SwitchSet){
            holder.binding.itemName.paintFlags = 0
            holder.binding.gotItemSwitch.isChecked = false
        }
        if(item.SwitchSet){
            holder.binding.itemName.paintFlags = STRIKE_THRU_TEXT_FLAG
            holder.binding.gotItemSwitch.isChecked = true
        }

        holder.binding.gotItemSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.binding.itemName.paintFlags = STRIKE_THRU_TEXT_FLAG
                item.SwitchSet = true
                viewModel.changeSwitch(item)
            } else {
                holder.binding.itemName.paintFlags = 0
                item.SwitchSet = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemGroceryItemBinding.inflate(layoutInflater,parent, false)
        return ViewHolder(binding)
    }

    fun get(adapterPosition: Int): Long {
        return getItem(adapterPosition).ItemId
    }

    class ViewHolder(val binding: ListItemGroceryItemBinding): RecyclerView.ViewHolder(binding.root)
}

//ItemAdapter for Manage Lists and Manage Categories
class ItemAdapterSimpleString : ListAdapter<String,ItemAdapterSimpleString.ViewHolder>(StringDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.itemName.text = item
    }

    fun getItemByPos(position: Int): String {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemNewListBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ListItemNewListBinding) : RecyclerView.ViewHolder(binding.root)
}




class GroceryItemDiffCallback : DiffUtil.ItemCallback<GroceryItem>() {
    override fun areItemsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
        return oldItem.ItemId == newItem.ItemId
    }

    override fun areContentsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
        return oldItem == newItem
    }
}
class StringDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}