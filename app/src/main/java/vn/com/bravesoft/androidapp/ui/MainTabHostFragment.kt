package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.viewbinding.library.fragment.viewBinding
import android.widget.Button
import androidx.fragment.app.FragmentManager
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.databinding.MainTabHostLayoutBinding
import vn.com.bravesoft.androidapp.utils.FragmentUtil

/**
 * Created by Khanh Ton on 2019-05-28.
 */
class MainTabHostFragment : BaseFragment(R.layout.main_tab_host_layout), OnClickListener {
    lateinit var mFragmentUtil: FragmentUtil
    lateinit var mFragmentManager: FragmentManager

    private val binding: MainTabHostLayoutBinding by viewBinding()

    private var indexMenu = 0

    companion object {
        fun newInstance(): MainTabHostFragment = MainTabHostFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentManager = childFragmentManager
        mFragmentUtil = FragmentUtil.instance
    }

    protected override fun init(view: View) {
        binding.menu1.setOnClickListener(this)
        binding.menu2.setOnClickListener(this)
        binding.menu3.setOnClickListener(this)
        binding.menu4.setOnClickListener(this)
        binding.menu5.setOnClickListener(this)
        binding.menu6.setOnClickListener(this)
        binding.menu1.performClick()
        binding.menu1.requestFocus()
    }

    private fun changeMenu(fragment: BaseFragment) {
        mFragmentUtil.replaceChildFragment(this, fragment, R.id.main_host_container)
    }

    override fun onClick(btn: View?) {
        btn?.let {
            if (view?.findViewById<Button>(it.id)?.hasFocus() == false) {
                view?.findViewById<Button>(it.id)?.requestFocus()
            }
            when (btn?.id) {
                R.id.menu1 -> {
                    indexMenu = 0
                    changeMenu(HomeFragment.newInstance())
                }
                R.id.menu2 -> {
                    indexMenu = 0
                    changeMenu(LoginFragment.newInstance())
                }
            }
        }
    }
}
