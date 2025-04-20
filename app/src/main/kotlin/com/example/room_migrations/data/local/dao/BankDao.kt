package com.example.room_migrations.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.room_migrations.data.local.entity.BankEntity

@Dao
interface BankDao {

     @Insert
     suspend fun insertBank(bank: BankEntity): Long

     @Query("SELECT * FROM bank WHERE bank_name = :name")
     suspend fun getBankByName(name: String): BankEntity?

     @Query("DELETE FROM bank WHERE bank_name = :name")
     suspend fun deleteBankByName(name: String): Int
}