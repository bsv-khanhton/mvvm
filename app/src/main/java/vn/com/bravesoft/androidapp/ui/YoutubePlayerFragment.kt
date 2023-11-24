package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.os.CountDownTimer
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
import vn.com.bravesoft.androidapp.view.MyYoutubePlayerUiController


class YoutubePlayerFragment : BaseFragment(R.layout.youtube_layer_layout) {
    private val binding: YoutubeLayerLayoutBinding by viewBinding()
    private var mYouTubePlayer: YouTubePlayer? = null
    private var videoID: String by argument()
    private var playerState: PlayerConstants.PlayerState = PlayerConstants.PlayerState.UNKNOWN
    private var defaultPlayerUiController: MyYoutubePlayerUiController? = null

    var countDownTimer: CountDownTimer? = null

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
                    MyYoutubePlayerUiController(binding.youtubePlayerView, youTubePlayer)
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
                defaultPlayerUiController?.updateState(state)
                playerState = state
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                super.onCurrentSecond(youTubePlayer, second)
                defaultPlayerUiController?.setCurrentTime(second)
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                super.onVideoDuration(youTubePlayer, duration)
                defaultPlayerUiController?.setEndTime(duration)
            }

        }, iFramePlayerOptions)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: KeyEventBus?) {
        "onMessageEvent: ${event?.keyCode}".logi()
        /*defaultPlayerUiController?.panelShow()
        defaultPlayerUiController?.focusPlayPauseButton()*/
        defaultPlayerUiController?.showHidePanel(true)
        resetCountdownCloseController()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private fun resetCountdownCloseController() {
        countDownTimer?.cancel()
        countDownTimer = object: CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                defaultPlayerUiController?.showHidePanel(false)
            }

        }
        countDownTimer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
