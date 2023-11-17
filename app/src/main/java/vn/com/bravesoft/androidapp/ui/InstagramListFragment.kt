package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.adapter.MediaInstagramAdapter
import vn.com.bravesoft.androidapp.base.BaseMVVMFragment
import vn.com.bravesoft.androidapp.databinding.InstagramListLayoutBinding
import vn.com.bravesoft.androidapp.di.FragmentComponent
import vn.com.bravesoft.androidapp.di.ViewModelFactory
import vn.com.bravesoft.androidapp.event.CallbackInstagram
import vn.com.bravesoft.androidapp.ext.bindComponent
import vn.com.bravesoft.androidapp.ext.getViewModel
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.model.InstagramDTO
import vn.com.bravesoft.androidapp.modelview.InstagramModelView
import vn.com.bravesoft.androidapp.recyclerview.ItemOffsetDecoration
import javax.inject.Inject

class InstagramListFragment : BaseMVVMFragment<InstagramModelView>(R.layout.instagram_list_layout),
    CallbackInstagram {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component: FragmentComponent by bindComponent()

    private var mAdapter: MediaInstagramAdapter? = null
    var mData: ArrayList<InstagramDTO?> = arrayListOf()

    private val binding: InstagramListLayoutBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component.inject(this)
        viewModel = getViewModel(viewModelFactory)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setupObserveModelView(viewModel: InstagramModelView?) {
        viewModel?.onLoadInstagramSuccessed?.observe(
            this,
            Observer { s ->
                "list instagram: ${s.data.size}".logi()
                mData.clear()
                mData.addAll(s.data)
                mAdapter?.notifyDataSetChanged()
            }
        )
    }

    override fun init(view: View) {
        mAdapter = MediaInstagramAdapter().apply {
            onCallback = this@InstagramListFragment
            datas = mData
        }

        binding.rvInstagramList.apply {
            layoutManager = GridLayoutManager(context,5, RecyclerView.VERTICAL, false)
            adapter = mAdapter
            setHasFixedSize(false)
            addItemDecoration(ItemOffsetDecoration(requireContext(), R.dimen._8sdp))
        }

        "list instagram load".logi()
        viewModel?.getInstagram()
    }

    override fun callbackSearch(media: InstagramDTO) {
        "Click InstagramDTO: $media".logi()
        val dialogFragment = InstagramDetailDialog()
        val args = Bundle()
        args.putString("media_url", media.mediaUrl)
        args.putString("media_type", media.mediaType)
        dialogFragment.arguments = args
        dialogFragment.show(childFragmentManager, "My  Fragment")
    }
}