package com.anuar.shoppinglist.data

import com.anuar.shoppinglist.domain.ShopItem
import com.anuar.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList= mutableListOf<ShopItem>()

    private var autoIncrementId=0
    override fun addShopItem(shopItem: ShopItem) {
        if(shopItem.id==ShopItem.UNDEFINED_ID){
            shopItem.id= autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldItem= getShopItem(shopItem.id)
        deleteShopItem(oldItem)
        addShopItem(shopItem)

    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id==shopItemId } ?:throw RuntimeException("there is no item with id $shopItemId")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList
    }

}