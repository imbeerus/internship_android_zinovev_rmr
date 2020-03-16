package com.lockwood.themoviedb.login.di.component

import com.lockwood.core.cache.di.provider.CacheToolsProvider
import com.lockwood.core.di.component.BaseFragmentComponent
import com.lockwood.core.di.provider.ApplicationProvider
import com.lockwood.core.di.scope.FeatureScope
import com.lockwood.core.network.di.provider.NetworkToolsProvider
import com.lockwood.themoviedb.login.di.module.LoginModule
import com.lockwood.themoviedb.login.presentation.ui.LoginFragment
import dagger.Component

@Component(
    dependencies = [
        ApplicationProvider::class,
        NetworkToolsProvider::class,
        CacheToolsProvider::class
    ],
    modules = [
        LoginModule::class
    ]
)
@FeatureScope
interface LoginComponent : BaseFragmentComponent<LoginFragment> {

    @Component.Builder
    interface Builder {

        fun applicationProvider(applicationProvider: ApplicationProvider): Builder

        fun networkToolsProvider(networkToolsProvider: NetworkToolsProvider): Builder

        fun cacheToolsProvider(cacheToolsProvider: CacheToolsProvider): Builder

        fun build(): LoginComponent

    }

}