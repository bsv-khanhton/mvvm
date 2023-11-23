package vn.com.bravesoft.androidapp.ui

import android.view.KeyEvent
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.leanback.widget.OnChildViewHolderSelectedListener
import androidx.leanback.widget.VerticalGridView
import androidx.recyclerview.widget.RecyclerView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.adapter.SearchAdapter
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.databinding.VideoListLayoutBinding
import vn.com.bravesoft.androidapp.event.CallbackSearch
import vn.com.bravesoft.androidapp.event.KeyEventBus
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.model.VideoDTO


class VideoListFragment : BaseFragment(R.layout.video_list_layout), CallbackSearch {
    private var searchAdapter: SearchAdapter? = null
    var data: ArrayList<VideoDTO?> = arrayListOf()

    private val binding: VideoListLayoutBinding by viewBinding()

    var positionFocus = 0

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
            adapter = searchAdapter
            setHasFixedSize(true)
            setNumColumns(5)
            horizontalSpacing = 20
            verticalSpacing = 20
        }

    }

    override fun callbackSearch(id: String) {
        "callbackSearch: $id".logi()
        mMainActivity?.replaceFragment(YoutubePlayerFragment.newInstance(id))
    }

    override fun callbackFocus(index: Int) {
        positionFocus = index
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: KeyEventBus?) {
        "onMessageEvent: ${event?.keyCode}".logi()
       /* if (event?.keyCode == 22) {
            val itemCount = binding.rvVideoList.adapter!!.itemCount
            val spanCount = 5
            if ((positionFocus + 1) % spanCount == 0) {
                // Manually move focus to the next row
                positionFocus++
                if (positionFocus < itemCount) {
                    binding.rvVideoList.getChildAt(positionFocus).requestFocus()
                }
            }
        }*/

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
