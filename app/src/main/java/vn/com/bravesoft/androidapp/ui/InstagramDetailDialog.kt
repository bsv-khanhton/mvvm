package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.ext.load
import vn.com.bravesoft.androidapp.ext.reactiveClick
import vn.com.bravesoft.androidapp.ext.visible
import vn.com.bravesoft.androidapp.view.InstagramImageView


class InstagramDetailDialog : DialogFragment() {
    private var btnClose: Button? = null
    private var ivInstagramDetail: InstagramImageView? = null
    private var playerView: StyledPlayerView? = null
    private var ivMute: ImageView? = null
    private var viewVideoPlayer: View? = null
    private var isMute = false
    private var player: ExoPlayer? = null

    @Nullable
    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.instagram_dialog_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnClose = view.findViewById(R.id.btnClose)
        ivInstagramDetail = view.findViewById(R.id.ivInstagramDetail)
        playerView = view.findViewById(R.id.video_view)
        ivMute = view.findViewById(R.id.ivMute)
        viewVideoPlayer = view.findViewById(R.id.viewVideoPlayer)
        btnClose?.reactiveClick {
            dismiss()
        }

        val mediaUrl = arguments?.getString("media_url")
        val mediaType = arguments?.getString("media_type")
        if (mediaType == "IMAGE") {
            ivInstagramDetail?.visible(true)
            viewVideoPlayer?.visible(false)
            ivInstagramDetail?.load(mediaUrl)
        } else {
            ivInstagramDetail?.visible(false)
            viewVideoPlayer?.visible(true)
            player = ExoPlayer.Builder(requireContext()).build()
            playerView?.player = player
            val mediaItem: MediaItem = MediaItem.fromUri(mediaUrl!!)
            player?.setMediaItem(mediaItem)
            player?.prepare()
            player?.play()

            ivMute?.reactiveClick {
                if (isMute) {
                    player?.volume = 1.0f
                    ivMute?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_unmute))
                } else {
                    player?.volume = 0.0f
                    ivMute?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_mute))
                }
                isMute = !isMute
            }
        }
        btnClose?.requestFocus()
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onDestroy() {
        player?.stop()
        super.onDestroy()
    }
}
