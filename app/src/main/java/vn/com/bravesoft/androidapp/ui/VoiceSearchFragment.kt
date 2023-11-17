package vn.com.bravesoft.androidapp.ui

import android.Manifest
import android.content.Context
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.content.ContextCompat
import com.nabinbhandari.android.permissions.PermissionHandler
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.databinding.VoiceSearchLayoutBinding
import vn.com.bravesoft.androidapp.ext.logd
import vn.com.bravesoft.androidapp.ext.reactiveClick
import vn.com.bravesoft.androidapp.helper.VoiceSearchManager

class VoiceSearchFragment : BaseFragment(R.layout.voice_search_layout) {

    private val binding: VoiceSearchLayoutBinding by viewBinding()

    private lateinit var voiceSearchManager: VoiceSearchManager

    override fun init(view: View) {
        voiceSearchManager = VoiceSearchManager(requireContext())
        handleEvent()
    }

    private fun handleEvent() {
        voiceSearchManager.setSearchListener(
            object : VoiceSearchManager.SearchListener {
                override fun onQuerySubmit(query: String) {
// khi kết thúc search
                    binding.textResult.text = query
                }

                override fun onStart() {
                    binding.textResult.text = "Start..."
                    binding.buttonVoiceSearch.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorAccent))
                }

                override fun onStop() {
                    binding.textResult.text = "Stop..."
                    binding.buttonVoiceSearch.setColorFilter(ContextCompat.getColor(requireContext(), R.color.main))
                }

                override fun onQueryChange(query: String) {
// đang trong quá trình nói
                    binding.textResult.text = query
                }
            }
        )

        binding.buttonVoiceSearch.reactiveClick {
            var permissionsType = Manifest.permission.RECORD_AUDIO
            checkSinglePermission(
                permissionType = permissionsType,
                message = "warning_allow_permission",
                handler = object : PermissionHandler() {
                    override fun onGranted() {
                        toggleVoiceSearch()
                    }

                    override fun onDenied(
                        context: Context?,
                        deniedPermissions: java.util.ArrayList<String>?
                    ) {
                        "-- Access Denide".logd()
                    }
                }
            )
        }
    }

    private fun toggleVoiceSearch() {
        if (voiceSearchManager.isRecognizing) {
            voiceSearchManager.stopRecognition()
        } else {
            voiceSearchManager.startRecognition()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        voiceSearchManager.finish()
    }
}
