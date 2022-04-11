package id.derysudrajat.alif.utils

import android.text.format.DateFormat
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    val Timestamp.day get(): Int = DateFormat.format("d", this.toDate()).toString().toInt()
    val Timestamp.month get(): Int = DateFormat.format("M", this.toDate()).toString().toInt()
    val Timestamp.year get(): Int = DateFormat.format("yyyy", this.toDate()).toString().toInt()
    val Timestamp.fullDate get() : String = DateFormat.format("EEE, dd MMMM yyyy", this.toDate()).toString()
    val Timestamp.dayOfWeek get() : String = DateFormat.format("EEE", this.toDate()).toString()
    val Timestamp.partialDate get() : String = DateFormat.format("EEE, dd MMMM", this.toDate()).toString()
    val Timestamp.all get() : String = DateFormat.format("EEEE, dd MMMM yyy - hh:mm a", this.toDate()).toString()
    val Timestamp.montYear get() : String = DateFormat.format("MMM, yyyy", this.toDate()).toString()
    val Timestamp.formatDate get() : String = DateFormat.format("dd-MM-yyyy", this.toDate()).toString()

    val Timestamp.hour get(): Int = DateFormat.format("k", this.toDate()).toString().toInt()
    val Timestamp.hourMinutes get(): String = DateFormat.format("kk:mm", this.toDate()).toString()
    val Timestamp.minutes get(): Int = DateFormat.format("m", this.toDate()).toString().toInt()
    val Timestamp.hourDesignation get(): String = DateFormat.format("a", this.toDate()).toString()

    val Date.stringFormat  get() : String = DateFormat.format("yyyy-M-d", this).toString()
    val String.timeStamp get() : Timestamp = Timestamp(SimpleDateFormat("yyyy-M-d", Locale.getDefault()).parse(this))

    fun getCalendar(year: Int, month: Int, day: Int) = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, day)
    }
    val indexOfDay get() : Int {
        val daysOfWeek = mutableListOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        return daysOfWeek.indexOf(Timestamp.now().dayOfWeek)
    }
}