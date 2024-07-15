package com.odisby.goldentomatoes.data.remote.di

import com.odisby.goldentomatoes.data.data.source.DetailsDataSource
import com.odisby.goldentomatoes.data.data.source.DiscoverSource
import com.odisby.goldentomatoes.data.data.source.SearchMoviesSource
import com.odisby.goldentomatoes.data.remote.source.DetailsDataSourceRemote
import com.odisby.goldentomatoes.data.remote.source.DiscoverSourceRemote
import com.odisby.goldentomatoes.data.remote.source.SearchMoviesSourceRemote
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {

    @Binds
    @Singleton
    fun bindsRemoteDetailsDataSource(impl: DetailsDataSourceRemote): DetailsDataSource.Remote

    @Binds
    @Singleton
    fun bindsRemoteDiscoverDataSource(impl: DiscoverSourceRemote): DiscoverSource.Remote

    @Binds
    @Singleton
    fun bindsRemoteSearchMoviesDataSource(impl: SearchMoviesSourceRemote): SearchMoviesSource.Remote

}
