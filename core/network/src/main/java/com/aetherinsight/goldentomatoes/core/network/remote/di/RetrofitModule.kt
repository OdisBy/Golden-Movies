package com.aetherinsight.goldentomatoes.core.network.remote.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.aetherinsight.goldentomatoes.core.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule{
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        val apiKeyInterceptor = Interceptor { chain ->
            val newRequest = chain.request()
                .newBuilder()
                .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                .build()

            chain.proceed(newRequest)
        }

        val regionAndLanguageInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            val newUrl = originalUrl.newBuilder()
                .addQueryParameter("language", "pt-BR")
                .addQueryParameter("region", "BR")
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }

        val loggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }


        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(regionAndLanguageInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext appContext: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(appContext)
            .collector(
                ChuckerCollector(
                    context = appContext,
                    showNotification = true,
                    retentionPeriod = RetentionManager.Period.ONE_HOUR
                )
            )
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }
}
