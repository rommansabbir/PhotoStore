package com.rommansabbir.photostore.features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rommansabbir.photostore.Adapter
import com.rommansabbir.photostore.R
import com.rommansabbir.photostore.base.*
import com.rommansabbir.photostore.base.data.PhotoSearchRequestModel
import com.rommansabbir.photostore.databinding.ActivityMainBinding
import com.rommansabbir.photostore.utils.LazyLoadingRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var adapter: Adapter

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
        loadImages()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = vm
        binding.lifecycleOwner = this
    }

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
        adapter.itemCallback = { photoModel ->
            photoModel.src?.original?.let {
                fullScreenImageView(it)
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.customize()
        binding.searchView.doAfterTextChanged { searchKeyword = it;loadImages() }
    }

    private fun loadImages(failure: (Failure) -> Unit = {}) {
        vm.setLoading(true)
        vm.searchPhotos(getRequestModel(),
            {
                vm.setLoading(false)
                adapter.submitList(it.photos)
            },
            {
                vm.setLoading(false)
                handleFailure(this, it)
                failure.invoke(it)
            }
        )
    }

    private fun getRequestModel(): PhotoSearchRequestModel = PhotoSearchRequestModel(
        currentPage,
        perPage,
        if (searchKeyword.isEmpty()) "Nature" else searchKeyword
    )

    private val listener = object : LazyLoadingRecyclerView.Listener {
        override fun loadMore() {
            if (currentPage > 0) {
                currentPage++
                showToast(this@MainActivity, "Current Page ++ : $currentPage")
                loadImages {
                    if (currentPage > 0) {
                        currentPage--
                        showToast(this@MainActivity, "Current Page -- : $currentPage")
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