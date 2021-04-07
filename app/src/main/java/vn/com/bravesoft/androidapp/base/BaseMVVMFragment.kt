package vn.com.bravesoft.androidapp.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

abstract class BaseMVVMFragment<M : BaseModelView>(rootLayout: Int) :
    BaseFragment(rootLayout), LifecycleOwner {

    protected var viewModel: M? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserveModelViewBase()
        setupObserveModelView(viewModel)
    }

    override fun onDestroyView() {
        if (viewModel != null) {
            hideLoading()
            viewModel?.detachView()
        }
        super.onDestroyView()
    }

    protected abstract fun setupObserveModelView(viewModel: M?)

    private fun setupObserveModelViewBase() {
        viewModel?.onLoadAPIFail?.observe(viewLifecycleOwner, Observer { msg -> loadAPIFail(msg) })

        viewModel?.onLoadAPIError?.observe(viewLifecycleOwner, Observer { throwable -> loadAPIError(throwable) })

        viewModel?.onShowLoading?.observe(
            viewLifecycleOwner,
            Observer { isShow ->
                if (isShow == true) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        )
    }
}
