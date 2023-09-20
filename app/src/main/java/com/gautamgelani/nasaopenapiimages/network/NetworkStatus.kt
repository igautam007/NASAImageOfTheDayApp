package com.gautamgelani.nasaopenapiimages.network

sealed class NetworkStatus{
    data class Running(val msg: String = "") : NetworkStatus()

    data class Success(val msg: String = "") : NetworkStatus()

    //Call failed or any exception occur
    data class Failed(
        val msg: String = "Something went wrong please try again later.",
        val id: Int? = 0,
    ) : NetworkStatus()

    //Auth token expired
    data class AuthToken(
        val msg: String = "Authentication token expired.",
    ) : NetworkStatus()
}
