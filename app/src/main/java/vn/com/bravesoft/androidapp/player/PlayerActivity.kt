package vn.com.bravesoft.androidapp.player

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.brightcove.player.event.AbstractEvent
import com.brightcove.player.event.EventType
import com.brightcove.player.mediacontroller.BrightcoveMediaController
import com.brightcove.player.model.DeliveryType
import com.brightcove.player.model.Video
import com.brightcove.player.view.BrightcoveExoPlayerVideoView
import com.brightcove.player.view.BrightcovePlayer
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.databinding.VideoPlayerControllerBinding
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.ext.reactiveClick
import vn.com.bravesoft.androidapp.ext.toDuration
import vn.com.bravesoft.androidapp.ext.visible
import vn.com.bravesoft.androidapp.utils.Constants
import java.net.URI
import java.net.URISyntaxException

class PlayerActivity : BrightcovePlayer() {
    private var bindingController: VideoPlayerControllerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        brightcoveVideoView = findViewById<BrightcoveExoPlayerVideoView>(R.id.brightcove_video_view)
        setContentView(R.layout.activity_player)
        val mediaController = BrightcoveMediaController(
            baseVideoView,
            R.layout.video_player_controller
        )
        val tvTitle = baseVideoView.findViewById<TextView>(R.id.title_text_view)
        tvTitle?.text = "Title demo video"
        baseVideoView.setMediaController(mediaController)
        bindingController = VideoPlayerControllerBinding.bind(
            mediaController.brightcoveControlBar
        ).also {
            it.playerSeekBar.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    it.seekBar.alpha = 0.0f
                    it.playerSeekBar.alpha = 1.0f
                } else {
                    it.seekBar.alpha = 1.0f
                    it.playerSeekBar.alpha = 0.0f
                }
            }

            it.playerSeekBar.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        if (fromUser) {
                            val seekToDuration = (progress.toLong()).coerceAtMost(baseVideoView.durationLong - 100)
                            baseVideoView.seekTo(seekToDuration)
                        } else {
                            "progress: $progress".logi()
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }
                }
            )

            it.replayButton.reactiveClick {
                replay()
            }
            it.forwardButton.reactiveClick {
                forward()
            }
            it.rewindButton.reactiveClick {
                rewind()
            }
        }
        val video: Video = Video.createVideo(
            "https://sdks.support.brightcove.com/assets/videos/hls/greatblueheron/greatblueheron.m3u8",
            DeliveryType.HLS
        )
        try {
            val myposterImage =
                URI("https://sdks.support.brightcove.com/assets/images/general/Great-Blue-Heron.png")
            video.properties[Video.Fields.STILL_IMAGE_URI] =
                myposterImage
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
        brightcoveVideoView.add(video)
        brightcoveVideoView.start()

        baseVideoView.apply {
            eventEmitter.on(EventType.DID_SET_VIDEO) {
                updateController()
            }

            eventEmitter.on(EventType.PLAY) {
                updateController()
            }

            eventEmitter.on(EventType.DID_PLAY) {
                updateController()
            }

            eventEmitter.on(EventType.PAUSE) {
                updateController()
            }

            eventEmitter.on(EventType.DID_PAUSE) {
                // viewModel.updateProgress(currentPositionLong.toInt())
                updateController()
            }

            eventEmitter.on(EventType.SEEK_TO) {
                updateController()
            }

            eventEmitter.on(EventType.DID_SEEK_TO) {
                // viewModel.updateProgress(currentPositionLong.toInt())
                updateController()
            }

            eventEmitter.on(EventType.PROGRESS) {
                // viewModel.updateProgress(currentPositionLong.toInt())
                updateController()
            }

            eventEmitter.on(EventType.COMPLETED) {
                if (currentVideo == null) return@on
                // viewModel.updateProgress(currentVideo.durationLong.toInt())
                postDelayed(
                    {
                        seekTo(currentVideo.durationLong)
                        updateController()
                    },
                    100
                )
            }
        }
    }

    private fun replay() {
        baseVideoView.stopPlayback()
        baseVideoView.seekTo(0L)
        baseVideoView.start()
    }

    private fun rewind(millisecond: Int = Constants.playerSeekMillisecond) {
        baseVideoView.apply {
            seekTo((currentPositionLong - millisecond).coerceAtLeast(0))
        }
    }

    private fun forward(millisecond: Int = Constants.playerSeekMillisecond) {
        baseVideoView.apply {
            if (currentVideo == null) return@apply
            seekTo((currentPositionLong + millisecond).coerceAtMost(baseVideoView.durationLong))
        }
    }

    private fun updateController() {
        baseVideoView.apply {
            if (currentVideo == null) return@apply

            bindingController?.apply {
                endTimeTextView.text = baseVideoView.durationLong.toInt().toDuration()
                currentTimeTextView.text = currentPositionLong.toInt().toDuration()
                playerSeekBar.max = baseVideoView.durationLong.toInt()
                playerSeekBar.progress = currentPositionLong.toInt()
                seekBar.max = baseVideoView.durationLong.toInt()
                seekBar.progress = currentPositionLong.toInt()

                val isCompleted = currentPositionLong == baseVideoView.durationLong
                if (isCompleted) {
                    replayButton.visible(true)
                    replayButton.requestFocus()
                    play.visible(false)
                    rewindButton.visible(false)
                    forwardButton.visible(false)
                    audioTracks.nextFocusUpId = replayButton.id

                    baseVideoView.brightcoveMediaController?.apply {
                        show()
                        isHideControllerEnable = false
                    }
                } else {
                    play.isSelected = isPlaying
                    play.visible(true)
                    if (replayButton.hasFocus()) play.requestFocus()
                    replayButton.visible(false)
                    rewindButton.visible(true)
                    forwardButton.visible(true)
                    audioTracks.nextFocusUpId = play.id

                    baseVideoView.brightcoveMediaController?.apply {
                        isHideControllerEnable = true
                        if (isShowing && replayButton.hasFocus()) play.requestFocus()
                    }
                }
            }
        }
    }
}
