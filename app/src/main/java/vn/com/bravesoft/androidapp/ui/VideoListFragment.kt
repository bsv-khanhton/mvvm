package vn.com.bravesoft.androidapp.ui

import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.adapter.SearchAdapter
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.databinding.VideoListLayoutBinding
import vn.com.bravesoft.androidapp.event.CallbackSearch
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.model.VideoDTO
import vn.com.bravesoft.androidapp.recyclerview.ItemOffsetDecoration

class VideoListFragment : BaseFragment(R.layout.video_list_layout), CallbackSearch {
    private var searchAdapter: SearchAdapter? = null
    var data: ArrayList<VideoDTO?> = arrayListOf()

    private val binding: VideoListLayoutBinding by viewBinding()

    override fun init(view: View) {
        data.add(VideoDTO("RK1K2bCg4J8"))
        data.add(VideoDTO("LXb3EKWsInQ"))
        data.add(VideoDTO("7PIji8OubXU"))
        data.add(VideoDTO("kJyRVdnRwZA"))
        data.add(VideoDTO("cRXbIih8TaM"))
        data.add(VideoDTO("7zm20grXcmw"))
        data.add(VideoDTO("RK1K2bCg4J8"))
        data.add(VideoDTO("LXb3EKWsInQ"))
        data.add(VideoDTO("7PIji8OubXU"))
        data.add(VideoDTO("kJyRVdnRwZA"))
        data.add(VideoDTO("cRXbIih8TaM"))
        data.add(VideoDTO("7zm20grXcmw"))
        data.add(VideoDTO("RK1K2bCg4J8"))
        data.add(VideoDTO("LXb3EKWsInQ"))
        data.add(VideoDTO("7PIji8OubXU"))
        data.add(VideoDTO("kJyRVdnRwZA"))
        data.add(VideoDTO("cRXbIih8TaM"))
        data.add(VideoDTO("7zm20grXcmw"))
        searchAdapter = SearchAdapter().apply {
            onCallbackSearch = this@VideoListFragment
            datas = data
        }

        binding.rvVideoList.apply {
            layoutManager = GridLayoutManager(context, 5, RecyclerView.VERTICAL, false)
            adapter = searchAdapter
            setHasFixedSize(false)
            addItemDecoration(ItemOffsetDecoration(requireContext(), R.dimen._8sdp))
        }
    }

    override fun callbackSearch(id: String) {
        "callbackSearch: $id".logi()
        mMainActivity?.replaceFragment(YoutubePlayerFragment.newInstance(id))
    }
}
