package com.example.weather.dagger.component.module

import com.example.weather.repository.interfaces.NetworkHelper
import com.example.weather.repository.impls.NetworkHelperImpl
import dagger.Binds
import dagger.Module


@Module
interface NetworkModule {

    @Binds
    fun providesNetworkHelper(helper: NetworkHelperImpl) : NetworkHelper
}