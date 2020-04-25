package vn.com.bravesoft.androidapp.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import vn.com.bravesoft.androidapp.di.scope.UiScope

/**
 * @author ex-cellpromote-ohta
 */
@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @UiScope
    fun provideActivity() = activity

    /*@Provides
    @UiScope
    fun provideEventTracker(activity: Activity): EventTracker = EventTracker(firebaseAnalytics, activity)*/

}
