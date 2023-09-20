package com.gautamgelani.nasaopenapiimages.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gautamgelani.nasaopenapiimages.data.DailyImage
import com.gautamgelani.nasaopenapiimages.network.NetworkViewModel
import com.gautamgelani.nasaopenapiimages.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    override val repository: MainRepository,
) : NetworkViewModel(context) {

    val imageList = MutableLiveData<DailyImage?>()

    fun doRequestForGetDataFromNASA() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.doRequestForGetDataFromNASA()
            imageList.postValue(result)
        }
    }
}