package vn.com.bravesoft.androidapp.ui

import android.view.View
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.ext.argument
import vn.com.bravesoft.androidapp.ext.logi

/**
 * Created by Khanh Ton on 2019-05-28.
 */
class MainTabHostFragment : BaseFragment(R.layout.main_tab_host_layout) {

    private var param1: String by argument()

    companion object {
        fun newInstance(param1: String): MainTabHostFragment  = MainTabHostFragment().apply {
            this.param1 = param1
        }
    }

    protected override fun init(view: View) {
        "param1: $param1".logi()
    }
}
