package com.anuar.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anuar.shoppinglist.domain.ShopItem
import com.anuar.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList= sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private val shopListLD=MutableLiveData<List<ShopItem>>()

    private var autoIncrementId=0

    init{
        for(i in 1..100) {
            val newItem = ShopItem("name$i", i, true)
            addShopItem(newItem)
        }
    }
    override fun addShopItem(shopItem: ShopItem) {
        if(shopItem.id==ShopItem.UNDEFINED_ID){
            shopItem.id= autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()

    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldItem= getShopItem(shopItem.id)
        deleteShopItem(oldItem)
        addShopItem(shopItem)

    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id==shopItemId } ?:throw RuntimeException("there is no item with id $shopItemId")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    fun updateList(){
        shopListLD.value= shopList.toList()
    }

}