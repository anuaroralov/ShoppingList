package com.anuar.shoppinglist.representation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anuar.shoppinglist.R
import com.anuar.shoppinglist.domain.ShopItem

class ShopListAdapter(private val onItemLongClickListener: (ShopItem)->Unit,
                      private val onItemClickListener:(ShopItem)->Unit):
                      ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(DiffCallback) {

    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvName:TextView = view.findViewById(R.id.tv_name)
        val tvCount:TextView = view.findViewById(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout=when(viewType){
            VIEW_TYPE_ENABLED->R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED->R.layout.item_shop_disabled
            else->throw RuntimeException("Unknown viewType $viewType")
        }

        val view=LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        if(getItem(position).enabled){
            return VIEW_TYPE_ENABLED
        }
        else{
            return VIEW_TYPE_DISABLED
        }
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()
        viewHolder.view.setOnLongClickListener {
            onItemLongClickListener(shopItem)
            true
        }
        viewHolder.view.setOnClickListener {
            onItemClickListener(shopItem)
        }
    }

    companion object{
        const val VIEW_TYPE_ENABLED=100
        const val VIEW_TYPE_DISABLED=101

        const val MAX_POOL_SIZE=20
    }

    object DiffCallback: DiffUtil.ItemCallback<ShopItem>() {
        override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
            return oldItem == newItem
        }
    }
}