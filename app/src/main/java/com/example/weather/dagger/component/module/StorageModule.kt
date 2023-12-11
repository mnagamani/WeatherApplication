package com.example.weather.dagger.component.module

import com.example.weather.repository.storage.SharedPreferenceStorage
import com.example.weather.repository.storage.Storage
import dagger.Binds
import dagger.Module

@Module
abstract class StorageModule {
    @Binds
    abstract fun provideStorage(storage: SharedPreferenceStorage): Storage
}