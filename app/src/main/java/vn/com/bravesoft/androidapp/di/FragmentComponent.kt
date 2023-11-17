package vn.com.bravesoft.androidapp.di

import dagger.Subcomponent
import vn.com.bravesoft.androidapp.di.scope.UiScope
import vn.com.bravesoft.androidapp.ui.InstagramListFragment
import vn.com.bravesoft.androidapp.ui.LoginFragment

/**
 * @author ex-cellpromote-ohta
 */
@UiScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: LoginFragment)
    fun inject(fragment: InstagramListFragment)

    @Subcomponent.Builder
    interface Builder {
        fun fragmentModule(fragmentModule: FragmentModule): Builder
        fun build(): FragmentComponent
    }
}
