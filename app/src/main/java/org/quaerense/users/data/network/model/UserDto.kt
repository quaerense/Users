package org.quaerense.users.data.network.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("email")
    @Expose
    val email: String?,

    @SerializedName("first_name")
    @Expose
    val firstName: String?,

    @SerializedName("last_name")
    @Expose
    val lastName: String?,

    @SerializedName("avatar")
    @Expose
    val avatarUrl: String?
)