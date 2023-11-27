package vn.com.bravesoft.androidapp.view

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.brightcove.player.mediacontroller.BrightcoveSeekBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.PlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.menu.YouTubePlayerMenu
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.utils.TimeUtilities
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.event.YoutubePlayerCallBack

class MyYoutubePlayerUiController(private val youTubePlayerView: YouTubePlayerView,
                                  private val youTubePlayer: YouTubePlayer,
                                  private val youtubePlayerCallBack: YoutubePlayerCallBack
) : PlayerUiController {
    val rootView: View = View.inflate(youTubePlayerView.context, R.layout.my_youtube_controller_layout, null)

    private val tvTitle = rootView.findViewById<TextView>(R.id.title_text_view)
    private val tvCurrentTime = rootView.findViewById<TextView>(R.id.current_time_text_view)
    private val tvEndTime = rootView.findViewById<TextView>(R.id.end_time_text_view)
    private val seekBar = rootView.findViewById<BrightcoveSeekBar>(R.id.seek_bar)
    private val playerSeekBar = rootView.findViewById<BrightcoveSeekBar>(R.id.player_seek_bar)
    private val playPauseButton = rootView.findViewById<Button>(R.id.play)

    private var isPlaying = false


    private var youTubePlayerMenu: YouTubePlayerMenu = DefaultYouTubePlayerMenu(
        youTubePlayerView.context
    )

    init {
        playerSeekBar.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                seekBar.alpha = 0.0f
                playerSeekBar.alpha = 1.0f
            } else {
                playerSeekBar.alpha = 0.0f
                seekBar.alpha = 1.0f
            }
        }

        playerSeekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        youtubePlayerCallBack.youtubePlayerCallback()
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )

        playerSeekBar.setOnClickListener {
            youTubePlayer.seekTo(playerSeekBar.progress.toFloat())
            playPauseButton.requestFocus()
        }

        playPauseButton.setOnClickListener { onPlayButtonPressed() }
    }

    override fun showVideoTitle(show: Boolean): PlayerUiController {
        return this
    }

    override fun setVideoTitle(videoTitle: String): PlayerUiController {
        tvTitle.text = videoTitle
        return this
    }

    override fun showUi(show: Boolean): PlayerUiController {
        return this
    }

    override fun showPlayPauseButton(show: Boolean): PlayerUiController {

        return this
    }

    override fun enableLiveVideoUi(enable: Boolean): PlayerUiController {
        return this
    }

    override fun setCustomAction1(
        icon: Drawable,
        clickListener: View.OnClickListener?
    ): PlayerUiController {
        return this
    }

    override fun setCustomAction2(
        icon: Drawable,
        clickListener: View.OnClickListener?
    ): PlayerUiController {
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
        return this
    }

    override fun showDuration(show: Boolean): PlayerUiController {
        return this
    }

    override fun showSeekBar(show: Boolean): PlayerUiController {
        return this
    }

    override fun showBufferingProgress(show: Boolean): PlayerUiController {
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

    fun setCurrentTime(currentTime: Float) {
        tvCurrentTime.text = TimeUtilities.formatTime(currentTime)
        seekBar.progress = currentTime.toInt()
        if (!playerSeekBar.hasFocus()) {
            playerSeekBar.progress = currentTime.toInt()
        }
    }

    fun setEndTime(endTime: Float) {
        tvEndTime.text = TimeUtilities.formatTime(endTime)
        seekBar.max = endTime.toInt()
        playerSeekBar.max = endTime.toInt()
        playPauseButton.requestFocus()
    }

    fun updateState(state: PlayerConstants.PlayerState) {
        when (state) {
            PlayerConstants.PlayerState.ENDED -> isPlaying = false
            PlayerConstants.PlayerState.PAUSED -> isPlaying = false
            PlayerConstants.PlayerState.PLAYING -> isPlaying = true
            else -> {}
        }

        updatePlayPauseButtonIcon(!isPlaying)
    }

    private fun updatePlayPauseButtonIcon(playing: Boolean) {
        playPauseButton.isSelected = !playing
    }

    private fun onPlayButtonPressed() {
        if (isPlaying)
            youTubePlayer.pause()
        else
            youTubePlayer.play()
    }

    fun showHidePanel(isShow: Boolean) {
        if (isShow) {
            rootView.alpha = 1.0f
        } else {
            rootView.alpha = 0f
            playPauseButton.requestFocus()
        }

    }
}