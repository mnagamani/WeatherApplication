package com.example.weather.dagger.component.module

import com.example.weather.dagger.component.scopes.FragmentScope
import com.example.weather.screen.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [])

interface SearchActivityModule {
    @[FragmentScope ContributesAndroidInjector(modules = [ SearchFragmentModule::class])]
    fun contributeSearchFragment() : SearchFragment
}