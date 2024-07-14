package com.odisby.goldentomatoes.data.discover.di

import com.odisby.goldentomatoes.data.discover.data_source.DiscoverApi
import com.odisby.goldentomatoes.data.discover.repository.DiscoverRepository
import com.odisby.goldentomatoes.data.discover.repository.DiscoverRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DiscoverModule {

    @Provides
    @Singleton
    fun provideDiscoverApi(retrofit: Retrofit): DiscoverApi =
        retrofit.create(DiscoverApi::class.java)

    @Provides
    @Singleton
    fun providesDiscoverRepository(discoverApi: DiscoverApi): DiscoverRepository =
        DiscoverRepositoryImpl(discoverApi)
}
