package org.quaerense.users.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserDbModel(
    @PrimaryKey val id: Int,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val avatarUrl: String?
)