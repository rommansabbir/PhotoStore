package com.rommansabbir.photostore

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rommansabbir.photostore.base.data.PhotoModel
import com.rommansabbir.photostore.base.executeBodyOrReturnNull
import com.rommansabbir.photostore.base.loadWithGlide
import com.rommansabbir.photostore.base.setVisibility
import com.rommansabbir.photostore.databinding.ContentItemPhotoBinding
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
class Adapter @Inject constructor(@ActivityContext val context: Context) :
    RecyclerView.Adapter<PhotoViewHolder>() {
    private val diffUtilsCallBack = object : DiffUtil.ItemCallback<PhotoModel>() {
        override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private var differ: AsyncListDiffer<PhotoModel> = AsyncListDiffer(this, diffUtilsCallBack)

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

    suspend fun addDataSet(dataList: MutableList<PhotoModel>, onSuccess: () -> Unit = {}) {
        CoroutineScope(Dispatchers.IO).launch {
            val list: MutableList<PhotoModel> = executeBodyOrReturnNull<MutableList<PhotoModel>> {
                val previousList =
                    ArrayList<PhotoModel>(
                        differ.currentList.toMutableList().toMutableSet().toMutableList()
                    )
                previousList.addAll(dataList)
                return@executeBodyOrReturnNull previousList
            } ?: mutableListOf()
            withContext(Dispatchers.Main) {
                differ.submitList(list)
                onSuccess.invoke()
            }
        }
    }

    internal var itemCallback: (model: PhotoModel) -> Unit = {}

    override fun getItemCount(): Int = differ.currentList.size

    internal fun collectDataSet(): MutableList<PhotoModel> = differ.currentList.toMutableList()

    fun clearDataSet() {
        this.differ.submitList(null)
        this.notifyDataSetChanged()
    }
}

class PhotoViewHolder(private val binding: ContentItemPhotoBinding, private val adapter: Adapter) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindView(position: Int) {
        executeBodyOrReturnNull {
            val model = adapter.collectDataSet()[position]
            binding.progressBar2.setVisibility(true)
            binding.imageView.loadWithGlide(model.src?.medium ?: "") {
                executeBodyOrReturnNull {
                    binding.progressBar2.setVisibility(false)
                }
            }
            binding.imageView.setOnClickListener { adapter.itemCallback.invoke(model) }
        }
    }
}

