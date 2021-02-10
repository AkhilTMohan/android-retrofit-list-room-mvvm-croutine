package com.c2info.akhil_systemtest.network

import com.c2info.akhil_systemtest.models.LoginModel
import com.c2info.akhil_systemtest.models.RequestUsersList
import com.c2info.akhil_systemtest.models.ResponseLogin
import com.c2info.akhil_systemtest.models.ResponseUsers
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIInterface {

    @POST("api/login")
    fun login(@Body message: LoginModel): Call<ResponseLogin>

    @GET("api/users")
    fun getUsersList(@Query("page") message: RequestUsersList): Call<ResponseUsers>
}