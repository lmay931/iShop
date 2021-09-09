package com.example.ishop

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ishop.database.GroceryItem
import com.example.ishop.databinding.ListItemGroceryItemBinding
import com.example.ishop.databinding.ListItemNewListBinding
import com.example.ishop.util.ItemTouchHelperAdapter


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
class ItemAdapterStrings : ListAdapter<String,ItemAdapterStrings.ViewHolder>(StringDiffCallback()){
//    ,ItemTouchHelperAdapter  {

    var mTouchHelper: ItemTouchHelper? = null

    fun setTouchHelper(touchHelper: ItemTouchHelper?) {
        mTouchHelper = touchHelper
    }

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

//    class ViewHolder(val binding: ListItemNewListBinding): RecyclerView.ViewHolder(binding.root),
//        View.OnClickListener, View.OnTouchListener, GestureDetector.OnGestureListener {
//
//        var mOnNoteListener: OnNoteListener? = null
//        var mGestureDetector: GestureDetector? = null
//
//        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//            mGestureDetector!!.onTouchEvent(event)
//            return true
//        }
//
//        override fun onDown(e: MotionEvent?): Boolean {
//            return false
//        }
//
//        override fun onShowPress(e: MotionEvent?) {}
//
//        override fun onSingleTapUp(e: MotionEvent?): Boolean {
//            mOnNoteListener!!.onNoteClick(adapterPosition)
//            return true
//        }
//
//        override fun onScroll(
//            e1: MotionEvent?,
//            e2: MotionEvent?,
//            distanceX: Float,
//            distanceY: Float
//        ): Boolean {
//            return true
//        }
//
////        override fun onLongPress(e: MotionEvent?) {
////            mTouchHelper.startDrag(this)
////        }
//
//        override fun onFling(
//            e1: MotionEvent?,
//            e2: MotionEvent?,
//            velocityX: Float,
//            velocityY: Float
//        ): Boolean {
//            return false
//        }
//
//        override fun onClick(v: View?) {
//            TODO("Not yet implemented")
//        }
//    }
//
//    interface OnNoteListener {
//        fun onNoteClick(position: Int)
//    }
//
//    override fun onItemMove(fromPosition: Int, toPosition: Int) {
//        val fromNote = getItem(fromPosition)
//        val newList = this.currentList.filterIndexed { index, _ ->
//            index != fromPosition
//        }.toMutableList()
//        newList.add(toPosition,fromNote)
//        submitList(newList)
//    }
//
//    override fun onItemSwiped(position: Int) {
//        submitList(this.currentList.filterIndexed { index, _ ->
//            index != position
//        })
//    }
//}

class StringDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
}