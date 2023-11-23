package vn.com.bravesoft.androidapp.ui

import android.view.View
import android.viewbinding.library.fragment.viewBinding
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.databinding.TestLayoutBinding

class Test : BaseFragment(R.layout.test_layout) {

    private val binding: TestLayoutBinding by viewBinding()

    override fun init(view: View) {
        binding.button1.requestFocus()
    }
}