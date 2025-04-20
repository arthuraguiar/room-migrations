package com.example.room_migrations.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.room_migrations.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM user WHERE first_name = :firstName")
    suspend fun getUserByFirstName(firstName: String): UserEntity?

    @Query("DELETE FROM user WHERE last_name = :lastName")
    suspend fun deleteAllByLastName(lastName: String): Int
}