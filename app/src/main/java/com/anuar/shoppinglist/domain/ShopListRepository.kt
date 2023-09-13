package com.anuar.shoppinglist.domain

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem){

    }
    fun deleteShopItem(shopItem: ShopItem){

    }
    fun editShopItem(shopItem: ShopItem){

    }
    fun getShopItem(shopItemId:Int):ShopItem{
        TODO()
    }
    fun getShopList():List<ShopItem>{
        TODO();
    }
}