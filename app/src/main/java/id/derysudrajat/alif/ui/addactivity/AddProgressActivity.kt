package id.derysudrajat.alif.ui.addactivity

import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import id.derysudrajat.alif.R
import id.derysudrajat.alif.data.model.ProgressTask
import id.derysudrajat.alif.databinding.ActivityAddProgressBinding
import id.derysudrajat.alif.utils.TimeUtils
import id.derysudrajat.alif.utils.TimeUtils.hour
import id.derysudrajat.alif.utils.TimeUtils.minutes
import id.derysudrajat.alif.utils.TimeUtils.partialDate
import id.derysudrajat.alif.utils.TimeUtils.stringFormat
import id.derysudrajat.alif.utils.TimeUtils.timeStamp
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

@AndroidEntryPoint
class AddProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProgressBinding
    private val viewModel: AddProgressViewModel by viewModels()
    private val scope = lifecycleScope
    private var selectedTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        populateTime(Timestamp.now().hour, Timestamp.now().minutes)
        populateDate(Timestamp.now())

        scope.launch { viewModel.repeating.collect { populateRepeating(it) } }
        scope.launch {
            viewModel.textRepeating.collect {
                if (it == "Not Repeating") binding.btnRepeating.isChecked = false
                binding.tvRepeating.text = it
            }
        }

        with(binding) {
            appBar.apply {
                tvTitle.text = buildString { append("Add Activity") }
                btnBack.setOnClickListener { finish() }
            }
            btnRepeating.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setRepeating(isChecked)
                rvRepeating.visibility = if (isChecked) View.VISIBLE else View.GONE
            }
            edtTitle.doAfterTextChanged { setUpButtonCreate(it.toString().isNotBlank()) }
            btnTime.setOnClickListener { showDatePicker() }
            btnCreateActivity.setOnClickListener {
                viewModel.addTask(
                    this@AddProgressActivity,
                    ProgressTask(
                        Random.nextLong(123, 1234567) * 7 * (TimeUtils.indexOfDay + 1),
                        edtTitle.text.toString(),
                        selectedTime.time.time,
                        "",
                        false
                    )
                ).also {
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }

    private fun ActivityAddProgressBinding.setUpButtonCreate(isNotBlank: Boolean) {
        this.btnCreateActivity.apply {
            setCardBackgroundColor(
                ContextCompat.getColorStateList(
                    context, if (isNotBlank) R.color.primary else R.color.gray
                )
            )
            isClickable = isNotBlank
        }
    }

    private fun showDatePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText("Select Reminder Date")
                .build()
        datePicker.show(supportFragmentManager, "Date Reminder")
        datePicker.addOnPositiveButtonClickListener {
            showTimePicker()
            val date = Date(it).stringFormat.timeStamp
            populateDate(date)
        }

    }

    private fun showTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(Timestamp.now().hour)
            .setMinute(Timestamp.now().minutes)
            .setTitleText("Set Reminder Time")
            .build()
        picker.show(supportFragmentManager, "Reminder")
        picker.addOnPositiveButtonClickListener {
            populateTime(picker.hour, picker.minute)
        }
    }

    private fun populateDate(date: Timestamp) {
        binding.tvDate.text = date.partialDate
        selectedTime.apply { time = date.toDate() }
    }

    private fun populateTime(hour: Int, minute: Int) {
        binding.tvTime.text = buildString {
            selectedTime.apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }
            val data = DateFormat.format("hh:mm a", Date(selectedTime.timeInMillis)).toString()
            append(data.uppercase())
        }
    }

    private fun populateRepeating(it: MutableList<Int>) {
        binding.rvRepeating.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = RepeatingAdapter(it) { position, isActive ->
                viewModel.setRepeating(position, isActive)
            }
        }
    }
}