package com.anuar.shoppinglist.representation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anuar.shoppinglist.R
import com.anuar.shoppinglist.domain.ShopItem

class ShopListAdapter(private val list: List<ShopItem>):RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvName:TextView = view.findViewById(R.id.tv_name)
        val tvCount:TextView = view.findViewById(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_shop_enabled,parent,false)
        return ShopItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = list[position]
        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()
        viewHolder.view.setOnLongClickListener {
            true
        }
    }
}