package org.quaerense.users.domain.model

data class User(
    val id: Int,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    var avatarUrl: String?
)