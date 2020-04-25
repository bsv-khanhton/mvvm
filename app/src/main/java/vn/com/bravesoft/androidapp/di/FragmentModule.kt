package vn.com.bravesoft.androidapp.di

import android.app.Activity
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import vn.com.bravesoft.androidapp.MainActivity
import vn.com.bravesoft.androidapp.di.scope.UiScope

/**
 * @author ex-cellpromote-ohta
 */
@Module
class FragmentModule(private val fragment: Fragment) {

    @UiScope
    @Provides
    fun provideActivity(): Activity = fragment.activity ?: throw NullPointerException("""
            Activity is Null. Please do the injection after the attach() of the fragment is called
            """)
    @UiScope
    @Provides
    fun provideMainActivity(): MainActivity = fragment.activity as MainActivity

    /*@Provides
    @UiScope
    fun provideEventTracker(firebaseAnalytics: FirebaseAnalytics, activity: Activity): EventTracker = EventTracker(firebaseAnalytics, activity)*/
}
