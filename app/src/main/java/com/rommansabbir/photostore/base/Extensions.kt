package com.rommansabbir.photostore.base

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

inline fun <T> executeBodyOrReturnNull(crossinline body: () -> T): T? {
    return try {
        body.invoke()
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}

fun View.setVisibility(value: Boolean, allowInvisible: Boolean = false) {
    this.visibility = if (value) View.VISIBLE else if (allowInvisible) View.INVISIBLE else View.GONE
}

fun ImageView.loadWithGlide(url: String, callback: (exception: GlideException?) -> Unit) {
    executeBodyOrReturnNull {
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback.invoke(e)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback.invoke(null)
                    return false
                }

            })
            .into(this)
    }
}