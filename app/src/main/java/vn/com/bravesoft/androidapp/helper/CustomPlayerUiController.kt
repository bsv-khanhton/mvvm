package vn.com.bravesoft.androidapp.helper

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.brightcove.player.mediacontroller.BrightcoveSeekBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import vn.com.bravesoft.androidapp.R

class CustomPlayerUiController(
    private val context: Context,
    customPlayerUi: View,
    private val youTubePlayer: YouTubePlayer,
    private val youTubePlayerView: YouTubePlayerView
) :
    AbstractYouTubePlayerListener() {
    private val playerTracker: YouTubePlayerTracker

    // panel is used to intercept clicks on the WebView, I don't want the user to be able to click the WebView directly.
    private var panel: View? = null
    private var progressbar: View? = null
    private var videoCurrentTimeTextView: TextView? = null
    private var videoDurationTextView: TextView? = null
    private var playPauseButton: Button? = null
    private var durationVideo: Float = 0F

    init {
        playerTracker = YouTubePlayerTracker()
        youTubePlayer.addListener(playerTracker)
        initViews(customPlayerUi)
    }

    private fun initViews(playerUi: View) {
        panel = playerUi.findViewById<View>(R.id.brightcove_control_bar)
        progressbar = playerUi.findViewById<BrightcoveSeekBar>(R.id.seek_bar)
        videoCurrentTimeTextView = playerUi.findViewById<TextView>(R.id.current_time_text_view)
        videoDurationTextView = playerUi.findViewById<TextView>(R.id.end_time_text_view)
        playPauseButton = playerUi.findViewById<Button>(R.id.play)
        playPauseButton?.setOnClickListener(View.OnClickListener { view: View? -> if (playerTracker.state == PlayerState.PLAYING) youTubePlayer.pause() else youTubePlayer.play() })
    }

    override fun onReady(youTubePlayer: YouTubePlayer) {
        // progressbar!!.visibility = View.GONE
    }

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerState) {
        if (state == PlayerState.PLAYING || state == PlayerState.PAUSED || state == PlayerState.VIDEO_CUED) {
            panel!!.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.transparent
                )
            )
            if (state == PlayerState.PLAYING) {
                playPauseButton?.isSelected = true
            } else if (state == PlayerState.PAUSED) {
                playPauseButton?.isSelected = false
            }
        } else if (state == PlayerState.BUFFERING) panel!!.setBackgroundColor(
            ContextCompat.getColor(
                context,
                android.R.color.transparent
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
        videoCurrentTimeTextView!!.text = second.toString() + ""
        val timeCurrentPer = (second / durationVideo) * 100
    }

    @SuppressLint("SetTextI18n")
    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
        durationVideo = duration
        videoDurationTextView!!.text = duration.toString() + ""
    }
}
