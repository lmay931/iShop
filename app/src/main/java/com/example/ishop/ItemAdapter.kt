package com.example.ishop

import android.annotation.SuppressLint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ishop.database.GroceryItem

class ItemAdapterShopping : RecyclerView.Adapter<ItemAdapterShopping.ViewHolder>() {
    var data = listOf<GroceryItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.item.text = item.Item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_grocery_item,parent,false)
        return ViewHolder(view)
    }
    class ViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {
        val item : TextView = itemView.findViewById(R.id.item_name)
    }
}

class ItemAdapterNewList : RecyclerView.Adapter<ItemAdapterNewList.ViewHolder>() {
    var data = listOf<GroceryItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.item.text = item.Item

//        holder.switch.setOnCheckedChangeListener { _, isChecked ->
//            if(isChecked) {holder.item.paintFlags = STRIKE_THRU_TEXT_FLAG}
//            else {holder.item.paintFlags = 0}
//            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_new_list,parent,false)
        return ViewHolder(view)
    }
    class ViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {
        val item : TextView = itemView.findViewById(R.id.item_name)
//        val switch: SwitchCompat = itemView.findViewById(R.id.gotItem_switch)
    }
}