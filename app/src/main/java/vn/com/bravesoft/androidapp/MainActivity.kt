package vn.com.bravesoft.androidapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.ui.LoginFragment
import vn.com.bravesoft.androidapp.utils.FragmentUtil

class MainActivity : AppCompatActivity() {

    lateinit var mFragmentManager: FragmentManager
    lateinit var mFragmentUtil: FragmentUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFragmentManager = supportFragmentManager
        mFragmentUtil = FragmentUtil.instance
        replaceFragment(LoginFragment())
    }

    private fun replaceFragment(fragment: BaseFragment) {
        replaceFragment(fragment, false)
    }

    fun replaceFragment(fragment: BaseFragment, isClearBacktrack: Boolean) {
        if (isClearBacktrack) {
            mFragmentUtil.resetBackstack(mFragmentManager)
        }
        mFragmentUtil.replaceFragment(mFragmentManager, fragment, R.id.main_activity_container)
    }

    override fun onBackPressed() {
        if (mFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
