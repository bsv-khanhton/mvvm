package vn.com.bravesoft.androidapp.helper

import vn.com.bravesoft.androidapp.MainActivity
import vn.com.bravesoft.androidapp.di.scope.UiScope
import vn.com.bravesoft.androidapp.ui.MainTabHostFragment
import javax.inject.Inject

@UiScope
class FragmentAggregator @Inject constructor(private val activity: MainActivity) {
    fun openMainTabHost(username: String) {
        activity.replaceFragment(MainTabHostFragment.newInstance(username), isClearBacktrack = true)
    }
}
