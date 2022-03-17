package org.quaerense.users.domain

data class User(
    val id: Int,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val avatarUrl: String?
)