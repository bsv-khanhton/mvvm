package vn.com.bravesoft.androidapp

import android.app.Application
import vn.com.bravesoft.androidapp.di.AppComponent
import vn.com.bravesoft.androidapp.di.DaggerAppComponent

/**
 * Created by Khanh Ton on 2020-04-20.
 */
class MyApplication : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().application(this).build()
    }
}
