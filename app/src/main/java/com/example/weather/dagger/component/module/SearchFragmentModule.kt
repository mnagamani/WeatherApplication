package com.example.weather.dagger.component.module

import androidx.fragment.app.Fragment
import com.example.weather.screen.search.SearchFragment
import dagger.Binds
import dagger.Module

@Module//(includes = [ViewModelFactoryModule :: class])
abstract class SearchFragmentModule {
    @Binds
    abstract fun bindSearchFragment(searchFragment: SearchFragment): Fragment

}