package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.view.View
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseFragment

/**
 * Created by Khanh Ton on 2019-05-28.
 */
class MainTabHostFragment : BaseFragment(R.layout.main_tab_host_layout) {

    protected override fun init(view: View) {

    }

    companion object {


        fun newInstance(): MainTabHostFragment {

            val args = Bundle()

            val fragment = MainTabHostFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
