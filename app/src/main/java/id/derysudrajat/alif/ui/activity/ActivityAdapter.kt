package id.derysudrajat.alif.ui.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import id.derysudrajat.alif.R
import id.derysudrajat.alif.data.model.ProgressTask
import id.derysudrajat.alif.databinding.ItemActivityBinding
import id.derysudrajat.alif.utils.TimeUtils.hourMinutes
import id.derysudrajat.easyadapter.EasyAdapter
import java.util.*

class ActivityAdapter(
    val list: List<ProgressTask>,
    private val onChecked: (id: Long, isChecked: Boolean) -> Unit
) : EasyAdapter<ProgressTask, ItemActivityBinding>(list.toMutableList()) {
    override fun create(parent: ViewGroup): ItemActivityBinding = ItemActivityBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    override fun onBind(binding: ItemActivityBinding, data: ProgressTask) {
        with(binding) {
            tvActivityLabel.text = data.title
            tvAlarm.text = Timestamp(Date(data.date)).hourMinutes
            ivCheck.apply {
                setImageResource(if (data.isCheck) R.drawable.ic_check_fill else R.drawable.ic_check_outline)
                imageTintList = ContextCompat.getColorStateList(
                    this.context,
                    if (data.isCheck) R.color.primary else R.color.black_60
                )
            }
            ivCheck.setOnClickListener { onChecked(data.id, !data.isCheck) }
            btnCheck.setOnClickListener { onChecked(data.id, !data.isCheck) }
        }
    }

}