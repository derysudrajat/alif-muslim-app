package id.derysudrajat.alif.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import id.derysudrajat.alif.data.model.Schedule
import id.derysudrajat.alif.databinding.ItemEventBinding
import id.derysudrajat.alif.utils.TimeUtils
import id.derysudrajat.alif.utils.TimeUtils.montYear
import id.derysudrajat.alif.utils.TimeUtils.stringFormat
import id.derysudrajat.alif.utils.TimeUtils.timeStamp
import id.derysudrajat.easyadapter.EasyAdapter

class EventAdapter(
    private val schedule: Schedule
) : EasyAdapter<String, ItemEventBinding>(
    if (schedule.hijriDate.holidays.isEmpty()) mutableListOf("No Event")
    else schedule.hijriDate.holidays.toMutableList()
){
    override fun create(parent: ViewGroup): ItemEventBinding = ItemEventBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    override fun onBind(binding: ItemEventBinding, data: String) {
        with(binding){
            tvDateDay.text = schedule.georgianDate.day.toString()
            tvDateIslamic.text = buildString {
                schedule.hijriDate.let {
                    append("${it.day} ${it.monthDesignation} ${it.year} ${it.yearDesignation}")
                }
            }
            tvHoliday.text = data
            schedule.georgianDate.let {
                tvDate.text = TimeUtils.getCalendar(it.year, it.month-1, it.day).time.stringFormat.timeStamp.montYear
            }
        }
    }

}