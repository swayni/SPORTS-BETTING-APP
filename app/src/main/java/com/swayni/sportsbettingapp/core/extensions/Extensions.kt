package com.swayni.sportsbettingapp.core.extensions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun View.isVisible(isVisible: Boolean){
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

fun ImageView.loadImage(resourceId: Int) {
    Glide.with(this)
        .load(resourceId)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(this)
}