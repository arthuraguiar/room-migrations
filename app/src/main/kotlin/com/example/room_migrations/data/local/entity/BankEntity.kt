package com.example.room_migrations.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank")
data class BankEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", defaultValue = "0")
    val id: Int,

    @ColumnInfo(name = "bank_name")
    val name: String,

    @ColumnInfo(name = "address")
    val address: String,
)
