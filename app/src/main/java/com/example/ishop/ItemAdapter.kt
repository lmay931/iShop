package com.example.ishop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ishop.database.GroceryItem

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
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