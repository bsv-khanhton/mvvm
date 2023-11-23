package vn.com.bravesoft.androidapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.lifecycle.Observer
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseMVVMFragment
import vn.com.bravesoft.androidapp.databinding.LoginLayoutBinding
import vn.com.bravesoft.androidapp.di.FragmentComponent
import vn.com.bravesoft.androidapp.di.ViewModelFactory
import vn.com.bravesoft.androidapp.ext.bindComponent
import vn.com.bravesoft.androidapp.ext.getViewModel
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.ext.reactiveClick
import vn.com.bravesoft.androidapp.helper.FragmentAggregator
import vn.com.bravesoft.androidapp.modelview.LoginModelView
import vn.com.bravesoft.androidapp.player.PlayerActivity
import javax.inject.Inject

class LoginFragment : BaseMVVMFragment<LoginModelView>(R.layout.login_layout) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var fragmentAggregator: FragmentAggregator

    private val binding: LoginLayoutBinding by viewBinding()

    private val component: FragmentComponent by bindComponent()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component.inject(this)
        viewModel = getViewModel(viewModelFactory)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setupObserveModelView(viewModel: LoginModelView?) {
        viewModel?.onLoginSuccessed?.observe(
            this,
            Observer { s ->
                fragmentAggregator.openMainTabHost(s)
            }
        )
    }

    override fun init(view: View) {
        binding.tvTitle?.text = "Demo JCom"
        binding.btnLogin.reactiveClick {
            // viewModel?.login(UserDTO("abc", "12343545"))
            "Login sucsseed".logi()
            // openImagePicker();
            val intent = Intent(activity, PlayerActivity::class.java)
            activity?.startActivity(intent)
            // mMainActivity?.replaceFragmentTest(VideoPlayerFragment())
        }

        binding.btnOpenBrowser.reactiveClick {
            openBrowser()
        }

        binding.btnOpenWebView.reactiveClick {
            openWebView()
        }

        binding.btnYoutube.reactiveClick {
            openVideoList()
        }

        binding.btnOpenVoiceSearch.reactiveClick {
            openVoiceSearch()
        }

        binding.btnOpenInstagramList.reactiveClick {
            openInstagram()
        }
    }

    private fun openVoiceSearch() {
        mMainActivity?.replaceFragment(VoiceSearchFragment())
    }

    companion object {
        fun newInstance(): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private fun openBrowser() {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bravesoft.co.jp/"))
            startActivity(browserIntent)
        } catch (ex: Exception) {
            "ex: $ex".logi()
        }
    }

    private fun openWebView() {
        mMainActivity?.replaceFragment(Test())
    }

    private fun openVideoList() {
        mMainActivity?.replaceFragment(VideoListFragment())
    }

    private fun openInstagram() {
        mMainActivity?.replaceFragment(InstagramListFragment())
    }
}
