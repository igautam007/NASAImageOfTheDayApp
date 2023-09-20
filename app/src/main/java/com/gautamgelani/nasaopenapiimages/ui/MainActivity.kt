package com.gautamgelani.nasaopenapiimages.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gautamgelani.nasaopenapiimages.databinding.ActivityMainBinding
import com.gautamgelani.nasaopenapiimages.network.NetworkStatus
import com.gautamgelani.nasaopenapiimages.network.NetworkStatus.Failed
import com.gautamgelani.nasaopenapiimages.network.NetworkStatus.Running
import com.gautamgelani.nasaopenapiimages.network.NetworkUtils
import com.gautamgelani.nasaopenapiimages.utils.Utils.showToast
import com.gautamgelani.nasaopenapiimages.utils.Utils.viewBinding
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        setUpObserver()
        if (NetworkUtils.isOnline(this)) {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.doRequestForGetDataFromNASA()
        } else {
            showToast(this, "Please check your internet connection")
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.doRequestForGetDataFromNASA()
        }
    }

    private fun setUpObserver() {
        viewModel.getNetworkStates().observe(this) {
            when (it) {
                is Running -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }

                is NetworkStatus.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                is Failed -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    showToast(this@MainActivity, it.msg)
                }

                else -> binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        viewModel.imageList.observe(this) {
            binding.swipeRefreshLayout.isRefreshing = false
            it?.let { images ->
                Logger.e(images.toString())
            }
        }
    }
}