package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.login_layout.*
import vn.com.bravesoft.androidapp.di.FragmentComponent
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.di.ViewModelFactory
import vn.com.bravesoft.androidapp.ext.bindComponent
import vn.com.bravesoft.androidapp.ext.getViewModel
import vn.com.bravesoft.androidapp.helper.FragmentAggregator

import vn.com.bravesoft.androidapp.base.BaseMVVMFragment
import vn.com.bravesoft.androidapp.modelview.LoginModelView
import javax.inject.Inject

class LoginFragment : BaseMVVMFragment<LoginModelView>(R.layout.login_layout, vn.com.bravesoft.androidapp.BR.vm) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var fragmentAggregator: FragmentAggregator

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

    override fun setupObserveModelView(mvvmModelView: LoginModelView?) {
        mvvmModelView?.onLoginSuccessed?.observe(this, Observer{ s ->
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
            fragmentAggregator.openMainTabHost()
        }
        )
    }


    override fun init(view: View) {
        tvTitle.setText("MVVM")
        viewModel?.username?.set("xyz")


    }

    companion object {
        fun newInstance(): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
