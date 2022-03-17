package org.quaerense.users.data.network.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class UserContainerDto(
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int?,

    @SerializedName("data")
    @Expose
    val users: List<UserDto>?
)