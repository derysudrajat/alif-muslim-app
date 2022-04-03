package id.derysudrajat.alif.data.model

import android.os.Parcelable
import id.derysudrajat.alif.repo.local.entity.ReminderEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class PrayerReminder(
    val index: Int,
    val time: String,
    var isReminded: Boolean
) : Parcelable {
    companion object{
        val EMPTY = listOf(
            PrayerReminder(0, "-", false),
            PrayerReminder(1, "-", false),
            PrayerReminder(2, "-", false),
            PrayerReminder(3, "-", false),
            PrayerReminder(4, "-", false),
            PrayerReminder(5, "-", false),
            PrayerReminder(6, "-", false),
        )
    }
}

fun List<PrayerReminder>.toReminderEntities(): List<ReminderEntity> {
    val listOfEntity = mutableListOf<ReminderEntity>()
    this.forEach { listOfEntity.add(it.toReminderEntity()) }
    return listOfEntity
}

fun PrayerReminder.toReminderEntity(): ReminderEntity =
    ReminderEntity(this.index, this.time, this.isReminded)