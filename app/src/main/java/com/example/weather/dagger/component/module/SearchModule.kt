package com.example.weather.dagger.component.module

import com.example.weather.dagger.component.scopes.ActivityScope
import com.example.weather.screen.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchModule {
    @[ActivityScope ContributesAndroidInjector(modules = [ SearchActivityModule::class])]
    abstract fun contributeSearchActivity() : SearchActivity
}