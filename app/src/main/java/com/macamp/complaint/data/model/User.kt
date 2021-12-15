package com.macamp.complaint.data.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("address")
    val address: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("current_team_id")
    val currentTeamId: Any,
    @SerializedName("department")
    val department: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("profile_photo_path")
    val profilePhotoPath: Any,
    @SerializedName("profile_photo_url")
    val profilePhotoUrl: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("usertype")
    val usertype: String,
    @SerializedName("ward_no")
    val wardNo: String
)