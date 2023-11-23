package vn.com.bravesoft.androidapp.view

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.PlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.menu.YouTubePlayerMenu
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.utils.FadeViewHelper
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBarListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import vn.com.bravesoft.androidapp.R

class CustomYoutubePlayerUiController(private val youTubePlayerView: YouTubePlayerView,
                                      private val youTubePlayer: YouTubePlayer
) : PlayerUiController {
    val rootView: View = View.inflate(youTubePlayerView.context, R.layout.youtube_control_layout, null)

    private var youTubePlayerMenu: YouTubePlayerMenu = DefaultYouTubePlayerMenu(
        youTubePlayerView.context
    )

    /**
     * View used for for intercepting clicks and for drawing a black background.
     * Could have used controlsContainer, but in this way I'm able to hide all the control at once by hiding controlsContainer
     */
    private val panel: View = rootView.findViewById(R.id.panel)

    private val controlsContainer: View = rootView.findViewById(R.id.controls_container)

    private val videoTitle: TextView = rootView.findViewById(R.id.video_title)
    private val liveVideoIndicator: TextView = rootView.findViewById(R.id.live_video_indicator)

    private val progressBar: ProgressBar = rootView.findViewById(R.id.progress)
    private val playPauseButton: ImageView = rootView.findViewById(R.id.play_pause_button)

    private val youtubePlayerSeekBar: YouTubePlayerSeekBar = rootView.findViewById(R.id.youtube_player_seekbar)
    private val fadeControlsContainer: FadeViewHelper = FadeViewHelper(controlsContainer)


    private var isPlaying = false
    private var isPlayPauseButtonEnabled = true


    private val youTubePlayerStateListener = object : AbstractYouTubePlayerListener() {
        override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
            updateState(state)

            if (state === PlayerConstants.PlayerState.PLAYING || state === PlayerConstants.PlayerState.PAUSED || state === PlayerConstants.PlayerState.VIDEO_CUED) {
                panel.setBackgroundColor(ContextCompat.getColor(panel.context, android.R.color.transparent))
                progressBar.visibility = View.GONE

                if (isPlayPauseButtonEnabled) playPauseButton.visibility = View.VISIBLE

                updatePlayPauseButtonIcon(state === PlayerConstants.PlayerState.PLAYING)

            } else {
                updatePlayPauseButtonIcon(false)

                if (state === PlayerConstants.PlayerState.BUFFERING) {
                    progressBar.visibility = View.VISIBLE
                    panel.setBackgroundColor(
                        ContextCompat.getColor(
                            panel.context,
                            android.R.color.transparent
                        )
                    )
                    if (isPlayPauseButtonEnabled) playPauseButton.visibility = View.INVISIBLE
                }

                if (state === PlayerConstants.PlayerState.UNSTARTED) {
                    progressBar.visibility = View.GONE
                    if (isPlayPauseButtonEnabled) playPauseButton.visibility = View.VISIBLE
                }
            }
        }

        override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {

        }
    }

    init {
        initClickListeners()
    }

    private fun initClickListeners() {
        youTubePlayer.addListener(youtubePlayerSeekBar)
        youTubePlayer.addListener(fadeControlsContainer)
        youTubePlayer.addListener(youTubePlayerStateListener)

        youtubePlayerSeekBar.youtubePlayerSeekBarListener = object : YouTubePlayerSeekBarListener {
            override fun seekTo(time: Float) = youTubePlayer.seekTo(time)
        }
        panel.setOnClickListener { fadeControlsContainer.toggleVisibility() }
        playPauseButton.setOnClickListener { onPlayButtonPressed() }
    }

    fun panelShow() {
        if (controlsContainer.visibility != View.VISIBLE) {
            panel.performClick()
        }
    }

    override fun showVideoTitle(show: Boolean): PlayerUiController {
        videoTitle.visibility = if (show) View.VISIBLE else View.GONE
        return this
    }

    override fun setVideoTitle(videoTitle: String): PlayerUiController {
        this.videoTitle.text = videoTitle
        return this
    }

    override fun showUi(show: Boolean): PlayerUiController {
        fadeControlsContainer.isDisabled = !show
        controlsContainer.visibility = if (show) View.VISIBLE else View.INVISIBLE
        return this
    }

    override fun showPlayPauseButton(show: Boolean): PlayerUiController {
        playPauseButton.visibility = if (show) View.VISIBLE else View.GONE

        isPlayPauseButtonEnabled = show
        return this
    }

    override fun enableLiveVideoUi(enable: Boolean): PlayerUiController {
        youtubePlayerSeekBar.visibility = if (enable) View.INVISIBLE else View.VISIBLE
        liveVideoIndicator.visibility = if (enable) View.VISIBLE else View.GONE
        return this
    }

    override fun setCustomAction1(
        icon: Drawable,
        clickListener: View.OnClickListener?
    ): PlayerUiController {
        showCustomAction1(true)
        return this
    }

    override fun setCustomAction2(
        icon: Drawable,
        clickListener: View.OnClickListener?
    ): PlayerUiController {
        showCustomAction2(true)
        return this
    }

    override fun showCustomAction1(show: Boolean): PlayerUiController {
        return this
    }

    override fun showCustomAction2(show: Boolean): PlayerUiController {
        return this
    }

    override fun showMenuButton(show: Boolean): PlayerUiController {
        return this
    }

    override fun setMenuButtonClickListener(customMenuButtonClickListener: View.OnClickListener): PlayerUiController {
        return this
    }

    override fun showCurrentTime(show: Boolean): PlayerUiController {
        youtubePlayerSeekBar.videoCurrentTimeTextView.visibility = if (show) View.VISIBLE else View.GONE
        return this
    }

    override fun showDuration(show: Boolean): PlayerUiController {
        youtubePlayerSeekBar.videoDurationTextView.visibility = if (show) View.VISIBLE else View.GONE
        return this
    }

    override fun showSeekBar(show: Boolean): PlayerUiController {
        youtubePlayerSeekBar.seekBar.visibility = if (show) View.VISIBLE else View.INVISIBLE
        return this
    }

    override fun showBufferingProgress(show: Boolean): PlayerUiController {
        youtubePlayerSeekBar.showBufferingProgress = show
        return this
    }

    override fun showYouTubeButton(show: Boolean): PlayerUiController {
        return this
    }

    override fun addView(view: View): PlayerUiController {
        return this
    }

    override fun removeView(view: View): PlayerUiController {
        return this
    }

    override fun getMenu(): YouTubePlayerMenu = youTubePlayerMenu

    override fun showFullscreenButton(show: Boolean): PlayerUiController {
        return this
    }

    override fun setFullscreenButtonClickListener(customFullscreenButtonClickListener: View.OnClickListener): PlayerUiController {
        return this
    }

    fun onPlayButtonPressed() {
        if (isPlaying)
            youTubePlayer.pause()
        else
            youTubePlayer.play()
    }

    private fun updateState(state: PlayerConstants.PlayerState) {
        when (state) {
            PlayerConstants.PlayerState.ENDED -> isPlaying = false
            PlayerConstants.PlayerState.PAUSED -> isPlaying = false
            PlayerConstants.PlayerState.PLAYING -> isPlaying = true
            else -> {}
        }

        updatePlayPauseButtonIcon(!isPlaying)
    }

    private fun updatePlayPauseButtonIcon(playing: Boolean) {
        val drawable = if (playing) R.drawable.ayp_ic_pause_36dp else R.drawable.ayp_ic_play_36dp
        playPauseButton.setImageResource(drawable)
    }

    fun focusPlayPauseButton() {
        playPauseButton.requestFocus()
    }
}