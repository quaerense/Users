package org.quaerense.users.data.network

import org.quaerense.users.data.network.model.UserContainerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUserList(
        @Query(PARAM_PAGE) page: Int = 0
    ): UserContainerDto


    companion object {

        private const val PARAM_PAGE = "page"
    }
}