package id.derysudrajat.alif.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import id.derysudrajat.alif.data.model.DateSchedule
import id.derysudrajat.alif.databinding.ItemCalendarBinding
import id.derysudrajat.easyadapter.EasyAdapter

class CalendarAdapter(
    hijriDate: DateSchedule,
    private val onClick: () -> Unit
) : EasyAdapter<DateSchedule, ItemCalendarBinding>(listOf(hijriDate).toMutableList()) {
    override fun create(parent: ViewGroup): ItemCalendarBinding {
        return ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBind(binding: ItemCalendarBinding, data: DateSchedule) {
        with(binding){
            tvIslamicDate.text = buildString {
                append("${data.day} ${data.monthDesignation} ${data.year} ${data.yearDesignation}")
            }

            tvIslamicEvent.text = buildString {
                if (data.holidays.isEmpty()) append("No Event Today")
                else data.holidays.forEachIndexed { index, s ->
                    append(s)
                    if (index != data.holidays.size - 1) append("\n")
                }
            }

            root.setOnClickListener { onClick() }
        }
    }

}