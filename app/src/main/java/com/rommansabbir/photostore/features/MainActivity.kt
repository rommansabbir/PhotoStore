package com.rommansabbir.photostore.features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rommansabbir.photostore.Adapter
import com.rommansabbir.photostore.R
import com.rommansabbir.photostore.base.customize
import com.rommansabbir.photostore.base.data.PhotoSearchRequestModel
import com.rommansabbir.photostore.base.doAfterTextChanged
import com.rommansabbir.photostore.base.handleFailure
import com.rommansabbir.photostore.databinding.ActivityMainBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupSearchView()

        /*Default search*/
        loadImages()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = vm
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.customize()
        binding.searchView.doAfterTextChanged { searchKeyword = it;loadImages() }
    }

    private fun loadImages() {
        vm.setLoading(true)
        vm.searchPhotos(getRequestModel(),
            {
                vm.setLoading(false)
                adapter.submitList(it.photos)
            },
            {
                vm.setLoading(false)
                handleFailure(this, it)
            }
        )
    }

    private fun getRequestModel(): PhotoSearchRequestModel = PhotoSearchRequestModel(
        currentPage,
        perPage,
        if (searchKeyword.isEmpty()) "Nature" else searchKeyword
    )

}