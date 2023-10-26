package com.anuar.shoppinglist.di

import com.anuar.shoppinglist.data.ShopListRepositoryImpl
import com.anuar.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {

    @Singleton
    @Binds
    fun bindShopListRepository(impl:ShopListRepositoryImpl): ShopListRepository
}