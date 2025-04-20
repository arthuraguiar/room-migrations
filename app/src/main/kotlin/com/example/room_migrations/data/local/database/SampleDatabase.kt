package com.example.room_migrations.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.room_migrations.data.local.dao.UserDao
import com.example.room_migrations.data.local.entity.UserEntity

private const val FIRST_VERSION = 1
private const val DATABASE_NAME = "sample_database"

@Database(
    entities = [UserEntity::class],
    version = FIRST_VERSION,
)
abstract class SampleDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

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