package com.tickledmedia.tmrohit.viewholder

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tickledmedia.tmrohit.R
import com.tickledmedia.tmrohit.models.ResponseItem
import com.tickledmedia.tmrohit.utils.ImageUtils
import kotlinx.android.synthetic.main.item_trending_repository.view.*
import java.text.NumberFormat


class TrendingRepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindDetailsToView(itemData: ResponseItem) {

        itemView.tvRepoName.text = itemData.name
        itemView.tvAuthorName.text = itemData.author
        itemView.tvDesc.text = itemData.description
        itemView.tvLanguage.text = itemData.language
        itemView.tvStars.text = NumberFormat.getIntegerInstance().format(itemData.stars).toString()
        itemView.tvForks.text = NumberFormat.getIntegerInstance().format(itemData.forks).toString()


        setTextViewDrawableColor(itemView.tvLanguage, itemData.languageColor)

        itemView.setOnClickListener {
            if (itemView.vGroup.visibility == View.VISIBLE) {
                itemView.vGroup.visibility = View.GONE
            } else {
                itemView.vGroup.visibility = View.VISIBLE
            }
        }

        ImageUtils.loadRoundedCornerImage(
                itemView.context,
                itemData.avatar,
                itemView.ivAvtar, R.dimen.dp_24,
                R.mipmap.img_default
        )

    }

    private fun setTextViewDrawableColor(textView: TextView, color: String?) {
        if (!TextUtils.isEmpty(color)) {
            for (drawable in textView.compoundDrawablesRelative) {
                drawable?.colorFilter = PorterDuffColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN)
            }
        }
    }
}