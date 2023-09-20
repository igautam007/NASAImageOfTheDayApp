package com.gautamgelani.nasaopenapiimages.network

import com.gautamgelani.nasaopenapiimages.constant.APIConstant
import com.gautamgelani.nasaopenapiimages.data.DailyImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @Headers("Content-Type:application/json")
    @GET(APIConstant.SEARCH_URL)
    suspend fun doRequestForGetDataFromNASA(
        @Query("api_key") apiKey: String = APIConstant.API_KEY
    ): Response<DailyImage>

}