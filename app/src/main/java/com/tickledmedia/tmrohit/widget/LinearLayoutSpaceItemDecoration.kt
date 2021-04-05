package com.tickledmedia.tmrohit.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearLayoutSpaceItemDecoration(spacing: Int) : RecyclerView.ItemDecoration() {
    private var spacing = 0
    private var position = 0

    init {
        this.spacing = spacing
    }

    override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
    ) {
        position = parent.getChildAdapterPosition(view) // item position
        outRect.left = spacing
        outRect.right = spacing
        outRect.bottom = spacing
        if (position < 1) outRect.top = spacing
    }
}