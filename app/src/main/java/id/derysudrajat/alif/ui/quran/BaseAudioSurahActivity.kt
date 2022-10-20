package id.derysudrajat.alif.ui.quran

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.derysudrajat.alif.data.model.Surah
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class BaseAudioSurahActivity : AppCompatActivity() {
    val surahViewModel: SurahViewModel by viewModels()
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    fun initMediaPlayer(surah: Surah) {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA).build()
            )
            if (surah.audioUrl.isNotBlank()) setDataSource(surah.audioUrl)
        }

        handler = Handler(mainLooper)
        runnable = object : Runnable {
            override fun run() {
                val current = mediaPlayer.currentPosition
                val progress = current.toFloat() / mediaPlayer.duration.toFloat()
                surahViewModel.setAudioProgress(progress)
                if (progress < 0.99) handler.postDelayed(this, 1L)
                else {
                    lifecycleScope.launch {
                        surahViewModel.setFinish(true)
                        surahViewModel.setAudioProgress(0.00001f)
                        surahViewModel.setCurrentDurationPosition(0)
                        delay(500)
                        surahViewModel.setFinish(null)
                        surahViewModel.setAudioProgress(0f)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(runnable)
    }

    fun onMediaStart(audioProgress: Float, position: Int) {
        mediaPlayer.apply {
            try {
                if (audioProgress == 0f) prepare()
                else resumeAudio(position)
                setOnPreparedListener { startAudio() }
            } catch (e: Exception) {
                startAudio()
            }
        }
    }

    private fun MediaPlayer.startAudio() {
        surahViewModel.setFinish(null)
        start()
        runnable.run()
    }

    private fun MediaPlayer.resumeAudio(position: Int) {
        seekTo(position)
        start()
        runnable.run()
    }

    fun onMediaPause() {
        mediaPlayer.pause()
        surahViewModel.setCurrentDurationPosition(mediaPlayer.currentPosition)
    }
}