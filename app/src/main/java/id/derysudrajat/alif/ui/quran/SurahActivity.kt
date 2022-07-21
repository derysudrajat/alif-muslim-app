package id.derysudrajat.alif.ui.quran

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.derysudrajat.alif.compose.SurahPage
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.data.model.Surah
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SurahActivity : AppCompatActivity() {

    private val quranViewModel: QuranViewModel by viewModels()
    private var currentSurah by mutableStateOf(Surah("", -1, -1, "", "", "", "", -1))
    private lateinit var mediaPlayer: MediaPlayer
    private var currentAudioProgress by mutableStateOf(0f)
    private var currentDurationPosition = 0
    private var isFinish: Boolean? = null
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlifTheme {
                SurahPage(
                    currentSurah, quranViewModel.listAyah, currentAudioProgress, isFinish,
                    this::onBack, this::onMediaStart, this::onMediaPause
                )
            }
        }
        intent.extras?.getParcelable<Surah>(EXTRA_SURAH)?.let {
            quranViewModel.getAyahFromSurah(it.index)
            currentSurah = it
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA).build()
                )
                if (it.audioUrl.isNotBlank()) setDataSource(it.audioUrl)
            }

            handler = Handler(mainLooper)
            runnable = object : Runnable {
                override fun run() {
                    val current = mediaPlayer.currentPosition
                    val progress = current.toFloat() / mediaPlayer.duration.toFloat()
                    currentAudioProgress = progress
                    if (progress < 0.99) handler.postDelayed(this, 1L)
                    else {
                        lifecycleScope.launch {
                            isFinish = true
                            currentAudioProgress = 0.00001f
                            currentDurationPosition = 0
                            delay(500)
                            isFinish = null
                            currentAudioProgress = 0f
                        }
                    }
                }
            }
        }
    }

    private fun onBack() = finish()

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(runnable)
    }

    private fun onMediaStart() {
        mediaPlayer.apply {
            try {
                if (currentAudioProgress == 0f) prepare()
                else resumeAudio()
                setOnPreparedListener { startAudio() }
            } catch (e: Exception) {
                startAudio()
            }
        }
    }

    private fun MediaPlayer.startAudio() {
        isFinish = null
        start()
        runnable.run()
    }

    private fun MediaPlayer.resumeAudio() {
        seekTo(currentDurationPosition)
        start()
        runnable.run()
    }

    private fun onMediaPause() {
        mediaPlayer.pause()
        currentDurationPosition = mediaPlayer.currentPosition
    }

    companion object {
        const val EXTRA_SURAH = "extra_surah"
    }
}