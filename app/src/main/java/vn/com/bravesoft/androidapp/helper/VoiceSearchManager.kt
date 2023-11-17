package vn.com.bravesoft.androidapp.helper

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.app.ActivityCompat.startActivityForResult

class VoiceSearchManager(private val context: Context) {

    private var searchListener: SearchListener? = null
    private var speechRecognizer: SpeechRecognizer? = null
    var isRecognizing = false

    init {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    }

    fun setSearchListener(listener: SearchListener) {
        searchListener = listener
    }

    fun stopRecognition() {
        if (!isRecognizing) return
        cancelRecognition()
    }


    fun startRecognition() {
        if (isRecognizing) return
        val res = context.checkCallingOrSelfPermission(Manifest.permission.RECORD_AUDIO)
        if (PackageManager.PERMISSION_GRANTED != res) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return
            }
        }
        isRecognizing = true
        setRecognitionListener()
        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
        speechRecognizer?.startListening(recognizerIntent)
    }

    fun submitRecognition() {
        if (!isRecognizing) return
        cancelRecognition()
    }

    fun finish() {
        cancelRecognition()
    }

    private fun cancelRecognition() {
        isRecognizing = false
        speechRecognizer?.cancel()
        speechRecognizer?.setRecognitionListener(null)
    }

    private fun setRecognitionListener() {
        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle) {
            }

            override fun onBeginningOfSpeech() {
                searchListener?.onStart()
            }

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(bytes: ByteArray) {}

            override fun onEndOfSpeech() {
                searchListener?.onStop()
            }

            override fun onError(error: Int) {
                stopRecognition()
            }

            override fun onResults(bundle: Bundle) {
                val matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    submitQuery(matches.first())
                }
                submitRecognition()
            }

            override fun onPartialResults(bundle: Bundle) {
                val results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (results == null || results.size == 0) {
                    return
                }
                searchListener?.onQueryChange(results.first())
            }

            override fun onEvent(i: Int, bundle: Bundle) {

            }
        })
    }

    private fun submitQuery(query: String) {
        if (query.isNotEmpty()) {
            searchListener?.onQuerySubmit(query)
        }
    }

    interface SearchListener {

        fun onQueryChange(query: String)

        fun onQuerySubmit(query: String)

        fun onStart()

        fun onStop()
    }
}