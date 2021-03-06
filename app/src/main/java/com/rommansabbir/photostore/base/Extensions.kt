package com.rommansabbir.photostore.base

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rommansabbir.photostore.R
import com.stfalcon.imageviewer.StfalconImageViewer
import java.util.*

/**
 * A generic API to execute a given body by handling try catch.
 * If error occurs during the executing return null instead of crash the application.
 *
 * @param body Body to execute.
 *
 * @return [T]
 */
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

/**
 * Load new image from remote source and post it to the respective [ImageView].
 * Notify client regarding the [GlideException] or resource loaded thorough the [callback].
 * Ex: Show a progress bar while the image is url and hide if error occur or resource loaded.
 *
 * @param url Image url.
 * @param callback Callback to get notified about error or success.
 */
fun ImageView.loadWithGlide(url: String, callback: (exception: GlideException?) -> Unit = {}) {
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


fun SearchView.customize() {
    val editText =
        this.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
    editText.setHintTextColor(
        ContextCompat.getColor(
            context,
            R.color.black
        )
    )
    editText.setTextColor(
        ContextCompat.getColor(
            context,
            R.color.black
        )
    )
    editText.textSize = 14f
}

/**
 * Add an action which will be invoked when the text is changing.
 *
 * @return the [SearchView.OnQueryTextListener] added to the [SearchView]
 */
inline fun SearchView.doAfterTextChanged(
    delay: Long = 500,
    crossinline onTextChangedDelayed: (text: String) -> Unit
) = doOnQueryTextListener(delay, onTextChangedDelayed)


/**
 * Add an action which will be invoked after the text changed.
 *
 * @return the [SearchView.OnQueryTextListener] added to the [SearchView]
 */
inline fun SearchView.doOnQueryTextListener(
    delay: Long,
    crossinline onTextChangedDelayed: (text: String) -> Unit
): SearchView.OnQueryTextListener {
    val queryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean = true
        override fun onQueryTextChange(newText: String?): Boolean {
            handlerPostDelayed(delay) {
                onTextChangedDelayed.invoke(newText ?: "")
            }
            return true
        }
    }
    this.setOnQueryTextListener(queryListener)
    return queryListener
}

/**
 * Show an image from remote source as full screen by using [Glide] and [StfalconImageViewer].
 *
 * @param url Image url
 */
fun Activity.fullScreenImageView(url: String) {
    StfalconImageViewer.Builder(this, mutableListOf(url)) { view, _ ->
        view.loadWithGlide(url) {}
    }.show()
}


var handlerDelayTimer: Timer = Timer()

/**
 * To execute a repetitive task at once only based on [delay].
 * All executing during the [delay] will be cancelled and only once will be invoked if the [delay]
 * is over.
 *
 * @param delay Delay.
 * @param onSuccess Callback to execute clients logic.
 */
inline fun handlerPostDelayed(delay: Long, crossinline onSuccess: () -> Unit) {
    handlerDelayTimer.cancel()
    handlerDelayTimer = Timer()
    handlerDelayTimer.schedule(object : TimerTask() {
        override fun run() {
            Handler(Looper.getMainLooper()).post {
                onSuccess.invoke()
            }
        }
    }, delay)
}
