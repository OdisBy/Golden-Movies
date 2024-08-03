package com.aetherinsight.goldentomatoes.data.remote.di

import com.aetherinsight.goldentomatoes.data.remote.api.DetailsApi
import com.aetherinsight.goldentomatoes.data.remote.api.DiscoverApi
import com.aetherinsight.goldentomatoes.data.remote.api.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModules {

    @Provides
    @Singleton
    fun provideDetailsApi(retrofit: Retrofit): DetailsApi =
        retrofit.create(DetailsApi::class.java)

    @Provides
    @Singleton
    fun provideDiscoverApi(retrofit: Retrofit): DiscoverApi =
        retrofit.create(DiscoverApi::class.java)

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi =
        retrofit.create(SearchApi::class.java)

}
