package id.derysudrajat.alif.ui.addactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.derysudrajat.alif.R
import id.derysudrajat.alif.databinding.ItemRepeatingBinding

class RepeatingAdapter(
    private val listOfRepeating: List<Int>,
    private val onClick: (position: Int, isActive: Boolean) -> Unit
) : RecyclerView.Adapter<RepeatingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemRepeatingBinding

    companion object {
        val defaultList = listOf("S", "M", "T", "W", "T", "F", "S")
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemRepeatingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isActive = listOfRepeating.contains(position)
        with(binding) {
            tvDay.apply {
                text = defaultList[position]
                setTextColor(
                    ContextCompat.getColor(
                        context, if (isActive) R.color.white else R.color.primary
                    )
                )
            }
            btnDay.apply {
                setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        if (isActive) R.color.primary else R.color.gray
                    )
                )
                setOnClickListener { onClick(position, isActive) }
            }
        }
    }

    override fun getItemCount(): Int = defaultList.size


}