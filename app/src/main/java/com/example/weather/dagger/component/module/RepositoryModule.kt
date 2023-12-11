package com.example.weather.dagger.component.module

import com.example.weather.repository.interfaces.WeatherRepository
import com.example.weather.repository.impls.WeatherRepositoryImpl

import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
interface RepositoryModule {

    @Binds
    fun provideRepository(repository: WeatherRepositoryImpl) : WeatherRepository

}