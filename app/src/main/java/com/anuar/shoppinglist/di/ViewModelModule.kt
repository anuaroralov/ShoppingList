package com.anuar.shoppinglist.di

import androidx.lifecycle.ViewModel
import com.anuar.shoppinglist.representation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule  {
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMyViewModel(impl:MainViewModel):ViewModel

}