package vn.com.bravesoft.androidapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.ui.LoginFragment
import vn.com.bravesoft.androidapp.utils.FragmentUtil

class MainActivity : AppCompatActivity() {

    lateinit var mFragmentManager: FragmentManager
    lateinit var mFragmentUtil: FragmentUtil
    var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFragmentManager = supportFragmentManager
        mFragmentUtil = FragmentUtil.instance
        imageView = this.findViewById(R.id.iv)
        replaceFragment(LoginFragment())
        "test fastlane2".logi()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === RESULT_OK) {
            // compare the resultCode with the
            // constant
            val selectedImageUri: Uri = data?.data!!
            if (null != selectedImageUri) {
                // update the image view in the layout
                "selectedImageUri: $selectedImageUri".logi()
                imageView?.setImageURI(selectedImageUri)
            }
        }
    }
}
