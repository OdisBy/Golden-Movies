package com.odisby.goldentomatoes.data.movies.remote.di

import com.odisby.goldentomatoes.data.movies.remote.api.DiscoverApi
import com.odisby.goldentomatoes.data.movies.remote.api.SearchApi
import com.odisby.goldentomatoes.data.movies.repositories.DiscoverRepository
import com.odisby.goldentomatoes.data.movies.remote.repositories.DiscoverRepositoryImpl
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

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi =
        retrofit.create(SearchApi::class.java)
}
