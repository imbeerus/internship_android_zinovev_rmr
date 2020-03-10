package com.lockwood.themoviedb.di

import com.lockwood.themoviedb.ui.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(
    private val activity: MainActivity
) {

    @Provides
    fun provideContext(): MainActivity = activity

}