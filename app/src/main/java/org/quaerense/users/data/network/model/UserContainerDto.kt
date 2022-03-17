package org.quaerense.users.data.network.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class UserContainerDto(
    @SerializedName("page")
    @Expose
    var page: Int?,

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int?,

    @SerializedName("data")
    @Expose
    var users: List<UserDto>?
)