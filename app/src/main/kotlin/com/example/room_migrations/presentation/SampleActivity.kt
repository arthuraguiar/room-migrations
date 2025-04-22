package com.example.room_migrations.presentation

import androidx.appcompat.app.AppCompatActivity
import com.example.room_migrations.data.local.database.SampleDatabase
import com.example.room_migrations.data.local.entity.UserEntity
import kotlinx.coroutines.runBlocking

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        val db = SampleDatabase.buildDatabase(applicationContext)
        val userDao = db.userDao()

        runBlocking {
            userDao.insertUser(UserEntity(0, "Bernardo", "slailati"))
        }
    }
}