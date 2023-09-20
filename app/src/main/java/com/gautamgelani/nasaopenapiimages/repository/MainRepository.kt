package com.gautamgelani.nasaopenapiimages.repository

import com.gautamgelani.nasaopenapiimages.data.DailyImage
import com.gautamgelani.nasaopenapiimages.network.ApiInterface
import com.orhanobut.logger.Logger
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : BaseRepository() {

    suspend fun doRequestForGetDataFromNASA(): DailyImage? {
        val call = apiInterface.doRequestForGetDataFromNASA()

        Logger.e(call.raw().request.url.toString())

        return runApiWithCustomResponse {
            call
        }
    }
}