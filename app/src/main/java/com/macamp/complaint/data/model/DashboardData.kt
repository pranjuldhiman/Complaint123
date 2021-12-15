package com.macamp.complaint.data.model


import com.google.gson.annotations.SerializedName

data class DashboardData(
    @SerializedName("all")
    val all: Int,
    @SerializedName("done")
    val done: Int,
    @SerializedName("in_process")
    val inProcess: Int,
    @SerializedName("return")
    val returnX: Int,
    @SerializedName("submitted")
    val submitted: Int,
    @SerializedName("yesterday_all")
    val yesterdayAll: Int,
    @SerializedName("yesterday_done")
    val yesterdayDone: Int,
    @SerializedName("yesterday_in_process")
    val yesterdayInProcess: Int,
    @SerializedName("yesterday_submitted")
    val yesterdaySubmitted: Int,
    @SerializedName("yesterday_unassigned")
    val yesterdayUnassigned: Int
)