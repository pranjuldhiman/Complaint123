package com.macamp.complaint.data.api

import com.macamp.complaint.data.model.Complaints
import com.macamp.complaint.data.model.DashboardData
import com.macamp.complaint.data.model.UserData
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET
    suspend fun getDashboardData(@Url url: String): Response<DashboardData>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<UserData>

    @Multipart
    @POST
    suspend fun workDoneProcess(
        @Url url: String,
        @Part image: MultipartBody.Part
    ): Response<ResponseBody>

    @POST
    suspend fun workDoneProcessWithoutFile(
        @Url url: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @PUT
    suspend fun deviceTokenUpdated(
        @Url url: String,
        @Field("device_token") deviceToken: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @PUT
    suspend fun actionReturnBack(
        @Url url: String,
        @Field("reasion") reason: String,
        @Field("id") id: String
    ): Response<ResponseBody>

    @GET
    suspend fun getInProcessComplaints(@Url url: String): Response<ArrayList<Complaints>>

    @GET
    suspend fun getReturnBackComplaints(@Url url: String): Response<ArrayList<Complaints>>

    @GET
    suspend fun getCompletedComplaints(@Url url: String): Response<ArrayList<Complaints>>

    @GET
    suspend fun getAllSubmittedComplaints(@Url url: String): Response<ArrayList<Complaints>>

    @GET
    suspend fun getAllComplaints(@Url url: String): Response<ArrayList<Complaints>>
}