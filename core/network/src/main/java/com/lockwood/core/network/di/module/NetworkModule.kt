package com.lockwood.core.network.di.module

import android.content.Context
import com.lockwood.core.network.BuildConfig
import com.lockwood.core.network.authenticator.UserToLoginAuthenticator
import com.lockwood.core.network.di.qualifier.BaseUrl
import com.lockwood.core.network.di.qualifier.ErrorInterceptor
import com.lockwood.core.network.di.qualifier.HeaderInterceptor
import com.lockwood.core.network.di.qualifier.LoggingInterceptor
import com.lockwood.core.network.interceptor.OkHttpErrorInterceptor
import com.lockwood.core.network.interceptor.OkHttpHeaderInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {

        private const val LOG_TAG_OK_HTTP = "OkHttp"

        private const val DEF_TIMEOUT_SECONDS = 40L
    }

    // Ловим 401 через Authenticator
    @Provides
    @Singleton
    fun provideTokenAuthenticator(context: Context): Authenticator {
        return UserToLoginAuthenticator(context)
    }

    // Ловим остальные сетевые ошибки через отдельный Interceptor
    @Provides
    @Singleton
    @ErrorInterceptor
    fun provideErrorInterceptor(context: Context): Interceptor {
        return OkHttpErrorInterceptor(context)
    }

    @Provides
    @Singleton
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag(LOG_TAG_OK_HTTP).d(message)
            }
        })
        // Чтобы быть уверенными, что утечки чувствительных данных не будет
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        return logging
    }

    @Provides
    @Singleton
    @HeaderInterceptor
    fun provideHeaderInterceptor(): Interceptor {
        return OkHttpHeaderInterceptor()
    }

    @Provides
    fun provideMoshiBuilder(): Moshi.Builder {
        return Moshi.Builder()
    }

    @Provides
    @Singleton
    fun provideMoshi(
        moshi: Moshi.Builder
    ): Moshi {
        return moshi.build()
    }

    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder()
            .connectTimeout(DEF_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(DEF_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(DEF_TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        okHttpClient: OkHttpClient.Builder,
        @HeaderInterceptor headerInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @ErrorInterceptor errorInterceptor: Interceptor,
        authenticator: Authenticator
    ): OkHttpClient {
        return okHttpClient.authenticator(authenticator)
            .addNetworkInterceptor(headerInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(errorInterceptor).build()
    }

    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

}