package com.macamp.complaint.data.model


import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("message")
    val message: List<String>
)