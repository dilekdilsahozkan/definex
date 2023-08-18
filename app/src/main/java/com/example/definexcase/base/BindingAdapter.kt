package com.example.definexcase.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("defineX:src")
fun ImageView.loadImage(src: String?) {
    Glide.with(context).clear(this)

    Glide.with(context).load(src)
        .centerCrop()
        .into(this)
}