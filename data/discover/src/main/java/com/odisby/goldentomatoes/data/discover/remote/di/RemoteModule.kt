package com.odisby.goldentomatoes.data.discover.remote.di

import com.odisby.goldentomatoes.data.discover.remote.api.DiscoverApi
import com.odisby.goldentomatoes.data.discover.repositories.DiscoverRepository
import com.odisby.goldentomatoes.data.discover.remote.repositories.DiscoverRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RemoteModule {

    @Provides
    @Singleton
    fun provideDiscoverApi(retrofit: Retrofit): DiscoverApi =
        retrofit.create(DiscoverApi::class.java)

    @Provides
    @Singleton
    fun providesDiscoverRepository(discoverApi: DiscoverApi): DiscoverRepository =
        DiscoverRepositoryImpl(discoverApi)
}
