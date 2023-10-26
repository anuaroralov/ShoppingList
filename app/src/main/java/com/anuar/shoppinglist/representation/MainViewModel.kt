package com.anuar.shoppinglist.representation

import androidx.lifecycle.ViewModel
import com.anuar.shoppinglist.domain.AddShopItemUseCase
import com.anuar.shoppinglist.domain.DeleteShopItemUseCase
import com.anuar.shoppinglist.domain.EditShopItemUseCase
import com.anuar.shoppinglist.domain.GetShopListUseCase
import com.anuar.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor( private val getShopListUseCase:GetShopListUseCase,
                     private val deleteShopItemUseCase:DeleteShopItemUseCase,
                     private val editShopItemUseCase:EditShopItemUseCase,
                     private val addShopItemUseCase:AddShopItemUseCase): ViewModel() {

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

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}