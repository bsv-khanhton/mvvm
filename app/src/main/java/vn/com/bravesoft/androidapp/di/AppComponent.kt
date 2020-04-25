package vn.com.bravesoft.androidapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import vn.com.bravesoft.androidapp.MyApplication
import javax.inject.Singleton

/**
 * @author KhanhTon
 */
@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun activityComponentBuilder(): ActivityComponent.Builder
    fun fragmentComponentBuilder(): FragmentComponent.Builder

    fun inject(application: MyApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}
