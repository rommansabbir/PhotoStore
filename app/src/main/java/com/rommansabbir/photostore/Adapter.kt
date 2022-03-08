package com.rommansabbir.photostore

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rommansabbir.photostore.databinding.ContentItemPhotoBinding

@SuppressLint("NotifyDataSetChanged")
class Adapter constructor(private val context: Context) : RecyclerView.Adapter<PhotoViewHolder>() {
    private val list: MutableList<PhotoModel> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            ContentItemPhotoBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            this
        )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bindView(position)
    }


    fun submitList(list: MutableList<PhotoModel>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

}


class PhotoViewHolder(private val binding: ContentItemPhotoBinding, private val adapter: Adapter) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindView(position: Int) {
        val isSuccessful: Boolean? = executeBodyOrReturnNull {
            return@executeBodyOrReturnNull true
        }
    }
}

inline fun <T> executeBodyOrReturnNull(crossinline body: () -> T): T? {
    return try {
        body.invoke()
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}