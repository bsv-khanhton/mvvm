package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.di.FragmentComponent
import vn.com.bravesoft.androidapp.ext.bindComponent
import vn.com.bravesoft.androidapp.helper.FragmentAggregator
import javax.inject.Inject

class SplashScreen : BaseFragment(R.layout.splash_layout) {
    @Inject
    lateinit var fragmentAggregator: FragmentAggregator

    private val component: FragmentComponent by bindComponent()

    var handler: Handler = Handler()

    companion object {
        fun newInstance(): SplashScreen = SplashScreen()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component.inject(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun init(view: View) {
        handler.postDelayed({
            fragmentAggregator.openMainTabHost()
        },1500)
    }
}