package com.anuar.shoppinglist.representation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.anuar.shoppinglist.data.ShopListRepositoryImpl
import com.anuar.shoppinglist.domain.AddShopItemUseCase
import com.anuar.shoppinglist.domain.DeleteShopItemUseCase
import com.anuar.shoppinglist.domain.EditShopItemUseCase
import com.anuar.shoppinglist.domain.GetShopListUseCase
import com.anuar.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository=ShopListRepositoryImpl(application)

    private val getShopListUseCase=GetShopListUseCase(repository)
    private val deleteShopItemUseCase=DeleteShopItemUseCase(repository)
    private val editShopItemUseCase=EditShopItemUseCase(repository)
    private val addShopItemUseCase=AddShopItemUseCase(repository)

    val shopList=getShopListUseCase.getShopList()

    private val scope= CoroutineScope(Dispatchers.IO)

    fun deleteShopItem(shopItem: ShopItem){
        scope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }

    }

    fun changeEnableState(shopItem: ShopItem){
        scope.launch {
            val newItem=shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newItem)
        }

    }
    fun editShopItem(shopItem: ShopItem){
        scope.launch {
            editShopItemUseCase.editShopItem(shopItem)
        }

    }

    fun addShopItem(shopItem: ShopItem){
        scope.launch{
            addShopItemUseCase.addShopItem(shopItem)
        }

    }
}