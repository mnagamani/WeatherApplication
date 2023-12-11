package com.example.weather.dagger.component

import android.app.Application
import android.content.Context
import com.example.weather.MyApplication
import com.example.weather.dagger.component.module.RepositoryModule
import com.example.weather.dagger.component.module.SearchModule
import com.example.weather.dagger.component.module.StorageModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, RepositoryModule::class, SearchModule::class, AndroidSupportInjectionModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationBind(application: Application): Builder

        @BindsInstance
        fun context(context: Context) : Builder

        fun build() : AppComponent
    }

    fun inject(application: MyApplication)
}
