package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseMVVMFragment
import vn.com.bravesoft.androidapp.di.FragmentComponent
import vn.com.bravesoft.androidapp.di.ViewModelFactory
import vn.com.bravesoft.androidapp.ext.bindComponent
import vn.com.bravesoft.androidapp.ext.getViewModel
import vn.com.bravesoft.androidapp.helper.FragmentAggregator
import vn.com.bravesoft.androidapp.modelview.TimeLineModelView
import javax.inject.Inject

class TimelineFragment : BaseMVVMFragment<TimeLineModelView>(R.layout.timeline_layout) {

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

    override fun setupObserveModelView(mvvmModelView: TimeLineModelView?) {

    }


    override fun init(view: View) {

    }

}
