package com.gautamgelani.nasaopenapiimages.repository

import androidx.lifecycle.MutableLiveData
import com.gautamgelani.nasaopenapiimages.network.NetworkStatus
import com.orhanobut.logger.Logger
import retrofit2.Response

open class BaseRepository {

    private var networkStatus = MutableLiveData<NetworkStatus>()

    fun getNetworkStates() = networkStatus

    suspend inline fun <T> runApiWithCustomResponse(
        crossinline apiCall: suspend () -> Response<T>,
    ): T? {
        getNetworkStates().postValue(NetworkStatus.Running())
        return try {
            //Invoke the function
            val response = apiCall.invoke()
            response.body()?.toString()?.let { Logger.e(it) }
            if (response.isSuccessful && response.body() != null) {
                //Alright our api call was success so post success
                getNetworkStates().postValue(NetworkStatus.Success())
                response.body()
            } else if (response.code() == 401) {
                getNetworkStates().postValue(NetworkStatus.AuthToken())
                null
            } else {
                getNetworkStates().postValue(NetworkStatus.Failed())
                Logger.e("Error", response.message())
                null
            }
        } catch (e: Exception) {
            //Lets dive into the exceptions
            if (e.message.equals("StandaloneCoroutine was cancelled", true))
                getNetworkStates().postValue(NetworkStatus.Failed())
            else getNetworkStates().postValue(
                NetworkStatus.Failed(
                    e.message ?: "",
                )
            )
            //We got nothing here so return null :( As our api has failed )
            e.message?.let { Logger.e("error", it) }
            null
        }
    }

}