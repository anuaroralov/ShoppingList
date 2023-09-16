package com.anuar.shoppinglist.representation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.anuar.shoppinglist.databinding.ActivityMainBinding
import com.anuar.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private lateinit var viewModel:viewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this).get(viewModel::class.java)
        viewModel.shopList.observe(this){
            showList(it)
        }


    }
    fun showList(shopItems: List<ShopItem>) {

    }
}