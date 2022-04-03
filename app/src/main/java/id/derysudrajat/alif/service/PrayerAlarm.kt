package id.derysudrajat.alif.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import id.derysudrajat.alif.R
import id.derysudrajat.alif.data.model.PrayerReminder
import id.derysudrajat.alif.data.model.hour
import id.derysudrajat.alif.data.model.minutes
import id.derysudrajat.alif.repo.PrayerRepository
import id.derysudrajat.alif.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class PrayerAlarm : BroadcastReceiver() {

    companion object {
        const val EXTRA_ALARM = "extra_alarm"

        const val NOTIFICATION_TITLE = "Prayer Reminder"
        private const val NOTIFICATION_ID = 101
        const val NOTIFICATION_REQUEST_CODE = 102
        const val CHANNEL_ID = "Reminder"
        const val CHANNEL_NAME = "Connect with other"
        private fun getScheduleName(index: Int) = when (index) {
            0 -> "Imsak"
            1 -> "Farj"
            2 -> "Sunrise"
            3 -> "Dhuhr"
            4 -> "Asr"
            5 -> "Maghrib"
            6 -> "Isha"
            else -> "-"
        }
    }

    @Inject
    lateinit var repository: PrayerRepository

    override fun onReceive(context: Context, intent: Intent) {
        intent.extras?.getParcelable<PrayerReminder>(EXTRA_ALARM)?.let {
            showAlarmNotification(context, it)
            if (it.index != 0 || it.index != 2) {
                val mediaPlayer = MediaPlayer.create(
                    context, if (it.index == 1) R.raw.adzan_fajr else R.raw.adzan_makkah
                )
                mediaPlayer.apply {
                    isLooping = false
                    start()
                }
            }
        }
    }

    fun setPrayerAlarm(context: Context, prayerReminder: PrayerReminder, showToast: Boolean? = true) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, PrayerAlarm::class.java)
        intent.putExtra(EXTRA_ALARM, prayerReminder)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, prayerReminder.time.hour)
            set(Calendar.MINUTE, prayerReminder.time.minutes)
            set(Calendar.SECOND, 0)
        }

        val pendingIntent =
            PendingIntent.getBroadcast(context, prayerReminder.index, intent, PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        if (showToast == true) Toast.makeText(
            context,
            "Reminder for ${getScheduleName(prayerReminder.index)} at ${prayerReminder.time} is set",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun cancelAlarm(context: Context, prayerReminder: PrayerReminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, PrayerAlarm::class.java)
        val requestCode = prayerReminder.index
        val pendingIntent = PendingIntent.getBroadcast(
            context, requestCode, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE

        )
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(
            context, "Reminder for ${getScheduleName(prayerReminder.index)} pray is unset",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showAlarmNotification(
        context: Context,
        prayerReminder: PrayerReminder
    ) {

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = Uri.parse(
            "android.resource://" + context.packageName + "/" + R.raw.adzan_fajr
        )

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_REQUEST_CODE, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) FLAG_IMMUTABLE
            else PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alif)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(
                buildString {
                    append("Now it's time for ")
                    append(getScheduleName(prayerReminder.index))
                    append(" pray at ")
                    append(prayerReminder.time)
                }
            )
            .setColor(ContextCompat.getColor(context, R.color.primary))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
                setSound(alarmSound, audioAttributes)
            }

            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(NOTIFICATION_ID, notification)
    }
}