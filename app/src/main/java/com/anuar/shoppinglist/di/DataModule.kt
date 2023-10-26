package com.anuar.shoppinglist.di

import android.app.Application
import com.anuar.shoppinglist.data.MyDatabase
import com.anuar.shoppinglist.data.ShopListDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
@Module
class DataModule {
    @Singleton
    @Provides
    fun provideShopListDao(application:Application):ShopListDao{
        return MyDatabase.getDatabase(application).shopListDao()
    }
}