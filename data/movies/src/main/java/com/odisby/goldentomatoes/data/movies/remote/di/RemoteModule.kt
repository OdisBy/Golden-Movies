package com.odisby.goldentomatoes.data.movies.remote.di

import com.odisby.goldentomatoes.data.movies.remote.api.DetailsApi
import com.odisby.goldentomatoes.data.movies.remote.api.DiscoverApi
import com.odisby.goldentomatoes.data.movies.remote.api.SearchApi
import com.odisby.goldentomatoes.data.movies.remote.repositories.DetailsRepositoryImpl
import com.odisby.goldentomatoes.data.movies.remote.repositories.DiscoverRepositoryImpl
import com.odisby.goldentomatoes.data.movies.repositories.DetailsRepository
import com.odisby.goldentomatoes.data.movies.repositories.DiscoverRepository
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
    fun providesDiscoverRepository(discoverApi: DiscoverApi): DiscoverRepository =
        DiscoverRepositoryImpl(discoverApi)

    @Provides
    @Singleton
    fun providesDetailsRepository(detailsApi: DetailsApi): DetailsRepository =
        DetailsRepositoryImpl(detailsApi)


    @Provides
    @Singleton
    fun provideDiscoverApi(retrofit: Retrofit): DiscoverApi =
        retrofit.create(DiscoverApi::class.java)


    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi =
        retrofit.create(SearchApi::class.java)

    @Provides
    @Singleton
    fun provideDetailsApi(retrofit: Retrofit): DetailsApi =
        retrofit.create(DetailsApi::class.java)
}
