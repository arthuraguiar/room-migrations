package com.example.room_migrations.presentation

import androidx.appcompat.app.AppCompatActivity
import com.example.room_migrations.data.local.database.SampleDatabase
import com.example.room_migrations.data.local.entity.BankEntity
import com.example.room_migrations.data.local.entity.UserEntity
import kotlinx.coroutines.runBlocking

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        val db = SampleDatabase.buildDatabase(applicationContext)
        val userDao = db.userDao()
        val bankDao = db.bankDao()

        runBlocking {
            userDao.insertUser(UserEntity(0, "Arthur", "Aguiar", 20.0))
            bankDao.insertBank(BankEntity(0, "Itau", "Rua 7 de setembro"))
        }
    }
}