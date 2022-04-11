package id.derysudrajat.alif.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import id.derysudrajat.alif.data.model.ProgressTask
import id.derysudrajat.alif.databinding.ItemProgressActivityBinding
import id.derysudrajat.easyadapter.EasyAdapter
import java.text.DecimalFormat

class ActivityTaskAdapter(
    listOfTask: List<ProgressTask>,
    private val onClick: () -> Unit,
): EasyAdapter<List<ProgressTask>,ItemProgressActivityBinding>(mutableListOf(listOfTask)) {
    override fun create(parent: ViewGroup): ItemProgressActivityBinding = ItemProgressActivityBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    override fun onBind(binding: ItemProgressActivityBinding, data: List<ProgressTask>) {
        with(binding){
            tvTotalTask.text = buildString { append("All(${data.size})") }

            val progress = data.filter { it.isCheck }.size.toDouble()
            val percentage = (progress / data.size.toDouble()) * 100

            tvProgress.text = buildString {
                if (data.isNotEmpty()) {
                    append("Progress: ")
                    if (percentage % 2.0 == 0.0) append(percentage.toInt())
                    else append(DecimalFormat("##.##").format(percentage))
                    append("%")
                } else append("No Activity yet, go add it")
            }

            linearProgressIndicator.apply {
                setProgress(progress.toInt())
                max = data.size
            }

            tvTaskRemains.text = buildString {
                if (data.isNotEmpty()) {
                    val remains = data.size - progress.toInt()
                    append(remains)
                    append(" Activity Remain")
                    if (remains > 1) append("s")
                }
            }

            root.setOnClickListener { onClick() }
        }

    }
}