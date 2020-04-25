package vn.com.bravesoft.androidapp.di

import dagger.Subcomponent
import vn.com.bravesoft.androidapp.di.scope.UiScope
import vn.com.bravesoft.androidapp.MainActivity

/**
 * @author Created by Khanh Ton on 2019-10-23.
 */
@UiScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    @Subcomponent.Builder
    interface Builder {
        fun activityModule(activityModule: ActivityModule): Builder
        fun build(): ActivityComponent
    }

}
