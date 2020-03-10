package com.lockwood.themoviedb.di

import com.lockwood.themoviedb.ui.MainActivity

fun MainActivity.inject() {
    DaggerMainComponent.builder()
        .activity(this)
        .mainActivityModule(MainActivityModule(this))
        .build()
        .inject(this)
}