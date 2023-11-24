package vn.com.bravesoft.androidapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.brightcove.player.model.DeliveryType
import com.brightcove.player.model.Video
import com.brightcove.player.view.BrightcoveExoPlayerVideoView
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.ext.load
import vn.com.bravesoft.androidapp.ext.reactiveClick
import vn.com.bravesoft.androidapp.ext.visible
import vn.com.bravesoft.androidapp.view.InstagramImageView
import java.net.URI
import java.net.URISyntaxException

class InstagramDetailDialog : DialogFragment() {
    private var btnClose: Button? = null
    private var ivInstagramDetail: InstagramImageView? = null
    private var brightcoveExoPlayerVideoView: BrightcoveExoPlayerVideoView? = null

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
        brightcoveExoPlayerVideoView = view.findViewById(R.id.brightcove_video_view)
        btnClose?.reactiveClick {
            dismiss()
        }

        val mediaUrl = arguments?.getString("media_url")
        val mediaType = arguments?.getString("media_type")
        if (mediaType == "IMAGE") {
            ivInstagramDetail?.visible(true)
            brightcoveExoPlayerVideoView?.visible(false)
            ivInstagramDetail?.load(mediaUrl)
        } else {
            ivInstagramDetail?.visible(false)
            brightcoveExoPlayerVideoView?.visible(true)
            val video2: Video = Video.createVideo(
                mediaUrl!!,
                DeliveryType.MP4
            )
            brightcoveExoPlayerVideoView?.mediaController = null
            brightcoveExoPlayerVideoView?.add(video2)
            brightcoveExoPlayerVideoView?.start()
            brightcoveExoPlayerVideoView?.brightcoveMediaController
        }
    }
}
