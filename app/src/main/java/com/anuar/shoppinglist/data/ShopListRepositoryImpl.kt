package com.anuar.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.anuar.shoppinglist.domain.ShopItem
import com.anuar.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao:ShopListDao,
    private val mapper:ShopListMapper
) : ShopListRepository {

    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.insertShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDao.insertShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = shopListDao.getShopList().map {
        mapper.mapListDbModelToListEntity(it)  }
}