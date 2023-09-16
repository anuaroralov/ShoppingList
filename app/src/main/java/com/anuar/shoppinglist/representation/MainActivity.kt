package com.anuar.shoppinglist.representation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
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
        adapter=ShopListAdapter()
        rvShopList.adapter =adapter


        viewModel.shopList.observe(this){
            adapter.list=it
        }

        binding.buttonAddShopItem.setOnClickListener{
            val newItem=ShopItem("Baaa",17,true)
            viewModel.add(newItem)
        }


    }

}