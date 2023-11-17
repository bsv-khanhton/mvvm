package vn.com.bravesoft.androidapp

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.greenrobot.eventbus.EventBus
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.event.KeyEventBus
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.ui.LoginFragment
import vn.com.bravesoft.androidapp.ui.VideoPlayerFragment
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
        //"test fastlane2".logi()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        "keyCode: $keyCode".logi()
        //Toast.makeText(this, "Key: $keyCode", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(KeyEventBus(keyCode, event))
        return super.onKeyDown(keyCode, event)
    }

     fun replaceFragment(fragment: BaseFragment) {
        replaceFragment(fragment, false)
    }

    fun replaceFragment(fragment: BaseFragment, isClearBacktrack: Boolean) {
        if (isClearBacktrack) {
            mFragmentUtil.resetBackstack(mFragmentManager)
        }
        mFragmentUtil.replaceFragment(mFragmentManager, fragment, R.id.main_activity_container)
    }

    fun replaceFragmentTest(fragment: Fragment, isClearBacktrack: Boolean) {
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
