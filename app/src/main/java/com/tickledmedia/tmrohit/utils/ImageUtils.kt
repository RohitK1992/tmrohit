package com.tickledmedia.tmrohit.utils

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tickledmedia.tmrohit.R

object ImageUtils {

    @JvmStatic
    fun loadImage(context: Context, url: String?, view: ImageView) {
        Glide.with(context).load(url).placeholder(R.color.colorPrimary).into(view)
    }

    @JvmStatic
    fun loadRoundedCornerImage(
        context: Context,
        url: String?,
        view: ImageView,
        cornerRadius: Int,
        @DrawableRes
        placeHolderId: Int
    ) {
        Glide.with(context).load(url).placeholder(placeHolderId)
            .apply(
                RequestOptions.bitmapTransform(
                    RoundedCorners(
                        cornerRadius
                        /*AppUtils.dpToPx(
                            context,
                            cornerRadius
                        )*/
                    )
                )
            )
            .into(view)
    }

    fun loadImage(activity: Context, url: String?, view: ImageView, placeHolderId: Int) {
        Glide.with(activity).load(url).placeholder(placeHolderId).into(view)
    }


}