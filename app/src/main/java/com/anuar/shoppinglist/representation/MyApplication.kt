package com.anuar.shoppinglist.representation

import android.app.Application
import com.anuar.shoppinglist.di.DaggerApplicationComponent

class MyApplication:Application() {
    val component by lazy{
        DaggerApplicationComponent.factory()
            .create(this)
    }
}