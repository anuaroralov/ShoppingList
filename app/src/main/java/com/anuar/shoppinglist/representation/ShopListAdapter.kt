package com.anuar.shoppinglist.representation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anuar.shoppinglist.R
import com.anuar.shoppinglist.domain.ShopItem

class ShopListAdapter(private val onItemLongClickListener: (ShopItem)->Unit,
                      private val onItemClickListener:(ShopItem)->Unit):
                      RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
    var list= listOf<ShopItem>()
        set(value){
            field=value
            notifyDataSetChanged()
        }
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

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        if(list[position].enabled){
            return VIEW_TYPE_ENABLED
        }
        else{
            return VIEW_TYPE_DISABLED
        }
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = list[position]
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
}