package com.lockwood.themoviedb.login.di.module

import com.lockwood.core.di.scope.FeatureScope
import com.lockwood.themoviedb.login.domain.CredentialsValidator
import dagger.Module
import dagger.Provides

@Module
class DomainProvidesModule {

    @Provides
    @FeatureScope
    fun bindCredentialsValidator(): CredentialsValidator = CredentialsValidator()

}