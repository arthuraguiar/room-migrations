package com.example.room_migrations.data.local.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.room_migrations.data.local.dao.BankDao
import com.example.room_migrations.data.local.dao.UserDao
import com.example.room_migrations.data.local.entity.UserEntity
import com.example.room_migrations.data.local.entity.BankEntity

private const val FIRST_VERSION = 1
private const val SECOND_VERSION = 2
private const val THIRD_VERSION = 3
private const val DATABASE_NAME = "sample_database"

@Database(
    entities = [UserEntity::class, BankEntity::class],
    version = THIRD_VERSION,
    autoMigrations = [
        AutoMigration(from = FIRST_VERSION, to = SECOND_VERSION),
        AutoMigration(from = SECOND_VERSION, to = THIRD_VERSION)
    ],
)
abstract class SampleDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun bankDao(): BankDao

    companion object {
        fun buildDatabase(context: Context): SampleDatabase {
            return Room.databaseBuilder(
                context,
                SampleDatabase::class.java,
                DATABASE_NAME
            ) .build()
        }
    }
}