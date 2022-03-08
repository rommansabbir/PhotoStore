package com.rommansabbir.photostore.features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rommansabbir.photostore.Adapter
import com.rommansabbir.photostore.R
import com.rommansabbir.photostore.base.data.PhotoSearchRequestModel
import com.rommansabbir.photostore.base.handleFailure
import com.rommansabbir.photostore.base.showToast
import com.rommansabbir.photostore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = vm
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = adapter

        //
        vm.setLoading(true)
        vm.searchPhotos(
            PhotoSearchRequestModel(
                1,
                100,
                "Nature"
            ),
            {
                vm.setLoading(false)
                showToast(this, it.toString())
                adapter.submitList(it.photos)
            },
            {
                vm.setLoading(false)
                handleFailure(this, it)
            }
        )
    }
}