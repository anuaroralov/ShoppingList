package com.anuar.shoppinglist.representation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.anuar.shoppinglist.databinding.ActivityMainBinding
import com.anuar.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var adapter: ShopListAdapter
    private lateinit var viewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        val rvShopList=binding.rvShopList
        adapter=ShopListAdapter(
            {
                viewModel.changeEnableState(it)
            },
            {
                TODO()
            }
        )
        rvShopList.adapter =adapter
        rvShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_DISABLED,ShopListAdapter.MAX_POOL_SIZE)
        rvShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_ENABLED,ShopListAdapter.MAX_POOL_SIZE)
        setupSwipeListener(rvShopList)

        viewModel.shopList.observe(this){
            adapter.list=it
        }

        binding.buttonAddShopItem.setOnClickListener{
            val newItem=ShopItem("Baaa",17,true)
            viewModel.add(newItem)
        }


    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.list[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

}