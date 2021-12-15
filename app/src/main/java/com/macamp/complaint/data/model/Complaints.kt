package com.macamp.complaint.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Complaints(
    @SerializedName("address")
    val address: String,
    @SerializedName("complaint")
    val complaint: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("cs_id")
    val csId: Int,
    @SerializedName("department")
    val department: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_pr")
    val isPr: String,
    @SerializedName("mediam_of_complaint")
    val mediamOfComplaint: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("parshad")
    val parshad: String,
    @SerializedName("resean")
    val resean: String?,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("ward_no")
    val wardNo: String,
    @SerializedName("work_done_img")
    val workDoneImg: Any?
) : Serializable