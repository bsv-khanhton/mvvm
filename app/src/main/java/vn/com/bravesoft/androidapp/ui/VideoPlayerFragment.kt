package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.view.View
import com.brightcove.player.model.DeliveryType
import com.brightcove.player.model.Video
import com.brightcove.player.view.BrightcoveExoPlayerVideoView
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseFragment

class VideoPlayerFragment : BaseFragment(R.layout.instagram_player) {
    lateinit var brightcoveExoPlayerVideoView: BrightcoveExoPlayerVideoView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        brightcoveExoPlayerVideoView = view.findViewById(R.id.brightcove_video_view)
    }

    override fun init(view: View) {
    }

    fun playVideo(url: String) {
        val video2: Video = Video.createVideo(
            url,
            DeliveryType.MP4
        )
        brightcoveExoPlayerVideoView.add(video2)
        brightcoveExoPlayerVideoView.start()
    }
}
