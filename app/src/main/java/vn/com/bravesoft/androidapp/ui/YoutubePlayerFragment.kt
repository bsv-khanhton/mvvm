package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.annotation.NonNull
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.databinding.YoutubeLayerLayoutBinding
import vn.com.bravesoft.androidapp.ext.argument

class YoutubePlayerFragment : BaseFragment(R.layout.youtube_layer_layout) {
    private val binding: YoutubeLayerLayoutBinding by viewBinding()

    private var videoID: String by argument()

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
        /*val customPlayerUi: View =
            binding.youtubePlayerView.inflateCustomPlayerUi(R.layout.video_player_controller)*/
        binding.youtubePlayerView.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener() {
                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
/*val customPlayerUiController = CustomPlayerUiController(
                    requireContext(),
                    customPlayerUi,
                    youTubePlayer,
                    binding.youtubePlayerView
                )
                youTubePlayer.addListener(customPlayerUiController)*/
                    youTubePlayer.loadVideo(videoID, 0f)
                }
            }
        )
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
