package com.example.weather

import android.app.Application
import com.example.weather.dagger.component.AppComponent
import com.example.weather.dagger.component.DaggerAppComponent
import com.example.weather.repository.storage.SharedPreferenceStorage
import com.example.weather.repository.storage.Storage
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class MyApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        initializeComponent().inject(this)
    }

     fun initializeComponent(): AppComponent {
        return DaggerAppComponent.builder().applicationBind(this).context(this).build()
    }

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector
}