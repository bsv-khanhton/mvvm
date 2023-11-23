package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.databinding.YoutubeLayerLayoutBinding
import vn.com.bravesoft.androidapp.event.KeyEventBus
import vn.com.bravesoft.androidapp.ext.argument
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.view.CustomYoutubePlayerUiController


class YoutubePlayerFragment : BaseFragment(R.layout.youtube_layer_layout) {
    private val binding: YoutubeLayerLayoutBinding by viewBinding()
    private var mYouTubePlayer: YouTubePlayer? = null
    private var videoID: String by argument()
    private var playerState: PlayerConstants.PlayerState = PlayerConstants.PlayerState.UNKNOWN
    private var defaultPlayerUiController: CustomYoutubePlayerUiController? = null

    companion object {
        fun newInstance(videoID: String): YoutubePlayerFragment = YoutubePlayerFragment().apply {
            this.videoID = videoID
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(binding.youtubePlayerView)
    }

    override fun init(view: View) {
        val iFramePlayerOptions: IFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(0)
            .build()

        binding.youtubePlayerView.initialize( object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                mYouTubePlayer = youTubePlayer
                defaultPlayerUiController =
                    CustomYoutubePlayerUiController(binding.youtubePlayerView, youTubePlayer)
                binding.youtubePlayerView.setCustomPlayerUi(defaultPlayerUiController!!.rootView)
                defaultPlayerUiController?.enableLiveVideoUi(false)
                defaultPlayerUiController?.setVideoTitle("Title of video")
                defaultPlayerUiController?.showYouTubeButton(false)
                defaultPlayerUiController?.showFullscreenButton(false)
                mYouTubePlayer?.loadVideo(videoID, 0f)
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                super.onStateChange(youTubePlayer, state)
                playerState = state
            }
        }, iFramePlayerOptions)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: KeyEventBus?) {
        "onMessageEvent: ${event?.keyCode}".logi()
        defaultPlayerUiController?.panelShow()
        defaultPlayerUiController?.focusPlayPauseButton()
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
