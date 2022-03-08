package com.rommansabbir.photostore

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rommansabbir.photostore.base.data.PhotoModel
import com.rommansabbir.photostore.base.executeBodyOrReturnNull
import com.rommansabbir.photostore.base.loadWithGlide
import com.rommansabbir.photostore.base.setVisibility
import com.rommansabbir.photostore.base.showToast
import com.rommansabbir.photostore.databinding.ContentItemPhotoBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
class Adapter @Inject constructor(@ActivityContext val context: Context) :
    RecyclerView.Adapter<PhotoViewHolder>() {
    val list: MutableList<PhotoModel> = mutableListOf()
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

    internal var itemCallback: (model: PhotoModel) -> Unit = {}

    override fun getItemCount(): Int = list.size

}

class PhotoViewHolder(private val binding: ContentItemPhotoBinding, private val adapter: Adapter) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindView(position: Int) {
        executeBodyOrReturnNull {
            val model = adapter.list[position]
            binding.progressBar2.setVisibility(true)
            binding.imageView.loadWithGlide(model.src?.original ?: "") {
                executeBodyOrReturnNull {
                    it?.let {
                        showToast(adapter.context, it.message.toString())
                    }
                    binding.progressBar2.setVisibility(false)
                }
            }
            binding.imageView.setOnClickListener { adapter.itemCallback.invoke(model) }
        }
    }
}

