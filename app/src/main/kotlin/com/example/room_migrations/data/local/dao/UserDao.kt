package com.example.room_migrations.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.room_migrations.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM user whERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?
}