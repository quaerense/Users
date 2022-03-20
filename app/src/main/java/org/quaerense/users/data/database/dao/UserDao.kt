package org.quaerense.users.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.quaerense.users.data.database.model.UserDbModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(users: List<UserDbModel>)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun get(id: Int): UserDbModel

    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<UserDbModel>>

    @Update
    suspend fun edit(user: UserDbModel)

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}