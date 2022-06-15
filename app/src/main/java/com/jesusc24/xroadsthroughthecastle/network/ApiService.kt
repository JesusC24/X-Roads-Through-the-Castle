package com.jesusc24.xroadsthroughthecastle.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import java.util.HashMap

interface ApiService {

    @POST("send")
    fun sendMessage(
        @HeaderMap headers: HashMap<String?, String?>?,
        @Body messageBody: String?
    ): Call<String?>?
}