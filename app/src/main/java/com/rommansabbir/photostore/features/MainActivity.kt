package com.rommansabbir.photostore.features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.rommansabbir.photostore.R
import com.rommansabbir.photostore.base.customize
import com.rommansabbir.photostore.base.doAfterTextChanged
import com.rommansabbir.photostore.base.executeBodyOrReturnNull
import com.rommansabbir.photostore.base.failure.Failure
import com.rommansabbir.photostore.base.fullScreenImageView
import com.rommansabbir.photostore.data.models.PhotoSearchRequestModel
import com.rommansabbir.photostore.databinding.ActivityMainBinding
import com.rommansabbir.photostore.utils.LazyLoadingRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    private var currentPage: Int = 1
    private val perPage: Int = 100
    private var searchKeyword: String = ""

    private lateinit var lazyLoadingRecyclerView: LazyLoadingRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        lazyLoadingRecyclerView = LazyLoadingRecyclerView.getInstance()
        setupAdapter()
        setupSearchView()

        /*Default search*/
        loadImages(true)

        //
        binding.button.setOnClickListener {
            when (binding.button.text) {
                2.toString() -> {
                    binding.button.text = 3.toString()
                    updateSpans(3)
                }
                3.toString() -> {
                    binding.button.text = 4.toString()
                    updateSpans(4)
                }
                4.toString() -> {
                    binding.button.text = 2.toString()
                    updateSpans(2)
                }
                else -> {
                    binding.button.text = 3.toString()
                    updateSpans(3)
                }
            }
        }
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = vm
        binding.lifecycleOwner = this
    }

    private fun setupAdapter() {
        binding.recyclerView.adapter = photoAdapter
        photoAdapter.itemCallback = { photoModel ->
            photoModel.src?.medium?.let {
                fullScreenImageView(it)
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.customize()
        binding.searchView.doAfterTextChanged { searchKeyword = it;loadImages(true) }
    }

    private fun loadImages(shouldClearOldList: Boolean = false, failure: (Failure) -> Unit = {}) {
        vm.setLoading(true)
        vm.searchPhotos(getRequestModel(),
            {
                executeBodyOrReturnNull {
                    vm.setLoading(false)
                    if (shouldClearOldList) photoAdapter.clearDataSet()
                    lifecycleScope.launch {
                        photoAdapter.addDataSet(it.photos)
                    }
                }
            },
            {
                vm.setLoading(false)
                failure.invoke(it)
            }
        )
    }

    private fun updateSpans(count: Int) {
        executeBodyOrReturnNull {
            binding.recyclerView.updateSpanCount(count) {
                binding.recyclerView.adapter = null
                setupAdapter()
                executeBodyOrReturnNull {
                    lazyLoadingRecyclerView.removeListener()
                    lazyLoadingRecyclerView.registerScrollListener(binding.recyclerView, listener)
                }
            }
        }
    }

    private fun getRequestModel(): PhotoSearchRequestModel = PhotoSearchRequestModel(
        currentPage,
        perPage,
        if (searchKeyword.isEmpty()) "Nature" else searchKeyword
    )

    private val listener = object : LazyLoadingRecyclerView.Listener {
        override fun loadMore() {
            executeBodyOrReturnNull {
                if (currentPage > 0) {
                    currentPage++
                    loadImages {
                        if (currentPage > 0) {
                            currentPage--
                        }
                    }
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        lazyLoadingRecyclerView.registerScrollListener(binding.recyclerView, listener)
    }

    override fun onStop() {
        super.onStop()
        lazyLoadingRecyclerView.removeListener()
    }

}