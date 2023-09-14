package com.anuar.shoppinglist.representation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anuar.shoppinglist.data.ShopListRepositoryImpl
import com.anuar.shoppinglist.domain.DeleteShopItemUseCase
import com.anuar.shoppinglist.domain.EditShopItemUseCase
import com.anuar.shoppinglist.domain.GetShopListUseCase
import com.anuar.shoppinglist.domain.ShopItem

class viewModel: ViewModel() {

    private val repository=ShopListRepositoryImpl

    private val getShopListUseCase=GetShopListUseCase(repository)
    private val deleteShopItemUseCase=DeleteShopItemUseCase(repository)
    private val editShopItemUseCase=EditShopItemUseCase(repository)

    val shopList=MutableLiveData<List<ShopItem>>()

    fun getShopList(){
        val list=getShopListUseCase.getShopList()
        shopList.value=list
    }

    fun deleteShopItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
        getShopList()
    }

    fun editShopItem(shopItem: ShopItem){
        editShopItemUseCase.editShopItem(shopItem)
        getShopList()
    }

    fun changeEnableState(shopItem: ShopItem){
        val newItem=shopItem.copy(enabled = !shopItem.enabled)
        editShopItem(newItem) 
    }
}