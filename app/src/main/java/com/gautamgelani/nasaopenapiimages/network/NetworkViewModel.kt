package com.gautamgelani.nasaopenapiimages.network

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.gautamgelani.nasaopenapiimages.repository.BaseRepository

abstract class NetworkViewModel(applicationContext: Context) :
    AndroidViewModel(applicationContext as Application) {

    abstract val repository: BaseRepository

    private val networkStatus: LiveData<NetworkStatus> by lazy { repository.getNetworkStates() }

    public fun getNetworkStates() = networkStatus
}