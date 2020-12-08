package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.login_layout.*
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseMVVMFragment
import vn.com.bravesoft.androidapp.di.FragmentComponent
import vn.com.bravesoft.androidapp.di.ViewModelFactory
import vn.com.bravesoft.androidapp.ext.bindComponent
import vn.com.bravesoft.androidapp.ext.getViewModel
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.ext.reactiveClick
import vn.com.bravesoft.androidapp.helper.FragmentAggregator
import vn.com.bravesoft.androidapp.model.UserDTO
import vn.com.bravesoft.androidapp.modelview.LoginModelView
import javax.inject.Inject

class LoginFragment : BaseMVVMFragment<LoginModelView>(R.layout.login_layout) {

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
        mvvmModelView?.onLoginSuccessed?.observe(
            this,
            Observer { s ->
                fragmentAggregator.openMainTabHost(s)
            }
        )
    }

    override fun init(view: View) {
        tvTitle.text = "MVVM"
        btnLogin.reactiveClick {
            viewModel?.login(UserDTO("abc", "12343545"))
            "Login sucsseed".logi()
        }
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
