package id.derysudrajat.alif.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import id.derysudrajat.alif.R
import id.derysudrajat.alif.data.model.PrayerReminder
import id.derysudrajat.alif.data.model.ProgressTask
import id.derysudrajat.alif.data.model.hour
import id.derysudrajat.alif.data.model.minutes
import id.derysudrajat.alif.repo.PrayerRepository
import id.derysudrajat.alif.ui.main.MainActivity
import id.derysudrajat.alif.utils.TimeUtils
import id.derysudrajat.alif.utils.TimeUtils.day
import id.derysudrajat.alif.utils.TimeUtils.hour
import id.derysudrajat.alif.utils.TimeUtils.hourMinutes
import id.derysudrajat.alif.utils.TimeUtils.minutes
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class PrayerAlarm : BroadcastReceiver() {

    companion object {
        const val EXTRA_ALARM = "extra_alarm"
        const val EXTRA_ALARM_ACTIVITY = "extra_alarm_activity"

        const val NOTIFICATION_TITLE = "Prayer Reminder"
        const val NOTIFICATION_TITLE_ACTIVITY = "Task Reminder"
        const val NOTIFICATION_REQUEST_CODE = 102
        const val CHANNEL_ID = "Reminder"
        const val CHANNEL_NAME = "Daily Reminder"
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
        intent.extras?.getParcelable<ProgressTask>(EXTRA_ALARM_ACTIVITY)?.let {
            if (Timestamp(Date(it.date)).hour >= Timestamp.now().hour) showAlarmNotification(
                context, it.id.toInt(), NOTIFICATION_TITLE_ACTIVITY,
                "Now it's time to do ${it.title}"
            )
        }
        intent.extras?.getParcelable<PrayerReminder>(EXTRA_ALARM)?.let {
            val reminderHour = it.time.split(":").first().toInt()
            if (reminderHour >= Timestamp.now().hour) {
                showAlarmNotification(context, it.index, NOTIFICATION_TITLE, buildString {
                    append("Now it's time for ")
                    append(getScheduleName(it.index))
                    append(" pray at ")
                    append(it.time)
                })
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
    }

    fun setPrayerAlarm(
        context: Context,
        prayerReminder: PrayerReminder,
        showToast: Boolean? = true
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, PrayerAlarm::class.java)
        intent.putExtra(EXTRA_ALARM, prayerReminder)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, prayerReminder.time.hour)
            set(Calendar.MINUTE, prayerReminder.time.minutes)
            set(Calendar.SECOND, 0)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, prayerReminder.index,
            intent, PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )
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

    fun setActivityAlarm(context: Context, progressTask: ProgressTask, showToast: Boolean?) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, PrayerAlarm::class.java)
        intent.putExtra(EXTRA_ALARM_ACTIVITY, progressTask)
        val calendar = Calendar.getInstance()
        val currentDate = Timestamp(Date(progressTask.date))

        val repeating = progressTask.repeating.split(" ")
        if (repeating.size != 1) repeating.forEach {
            if (it.isNotBlank()) {
                // set multiple time and interval 7 and [id + interval]
                var interval = it.toInt() - TimeUtils.indexOfDay
                if (interval == -1) interval = 7

                calendar.apply {
                    set(Calendar.DAY_OF_MONTH, currentDate.day + interval)
                    set(Calendar.HOUR_OF_DAY, currentDate.hour)
                    set(Calendar.MINUTE, currentDate.minutes)
                    set(Calendar.SECOND, 0)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context, (progressTask.id + interval).toInt(),
                    intent, PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
                )
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY * 7,
                    pendingIntent
                )
            }
        } else {
            calendar.apply {
                set(Calendar.HOUR_OF_DAY, currentDate.hour)
                set(Calendar.MINUTE, currentDate.minutes)
                set(Calendar.SECOND, 0)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context, progressTask.id.toInt(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            )
            when (repeating.first().toInt()) {
                7 -> alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
                else -> alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        }
        if (showToast == true) Toast.makeText(
            context,
            "Reminder for ${progressTask.title} at ${currentDate.hourMinutes} is set",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun cancelActivityAlarm(context: Context, progressTask: ProgressTask) {
        val repeating = progressTask.repeating.split(" ")
        if (repeating.size != 1) {
            repeating.forEach {
                if (it.isNotBlank()) {
                    var interval = it.toInt() - TimeUtils.indexOfDay
                    if (interval == -1) interval = 7
                    cancelAlarm(context, (progressTask.id + interval).toInt())
                }
            }
        } else cancelAlarm(context, progressTask.id.toInt())
        Toast.makeText(context, "Reminder for ${progressTask.title} is unset", Toast.LENGTH_SHORT)
            .show()
    }

    fun cancelAlarm(context: Context, id: Int, message: String? = "") {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, PrayerAlarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        message?.let {
            if (it.isNotBlank()) {
                val text = "Reminder for ${getScheduleName(id)} pray is unset"
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAlarmNotification(
        context: Context,
        id: Int,
        title: String,
        content: String
    ) {

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_REQUEST_CODE, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) FLAG_IMMUTABLE
            else PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alif)
            .setContentTitle(title)
            .setContentText(content)
            .setColor(ContextCompat.getColor(context, R.color.primary))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            }

            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(id, notification)
    }
}