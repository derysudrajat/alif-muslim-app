package id.derysudrajat.alif.utils

import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton

object SkeletonUtils {
    fun RecyclerView.buildSkeleton(layout: Int, count: Int): Skeleton {
        val skeleton = this.applySkeleton(layout, itemCount = count)
        skeleton.configure()
        return skeleton
    }

    fun Skeleton.configure() {
        this.apply {
            maskCornerRadius = 50f
            showShimmer = true
        }
    }
}