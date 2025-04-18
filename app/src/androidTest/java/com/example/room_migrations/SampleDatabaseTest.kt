package com.example.room_migrations

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.room_migrations.data.local.dao.UserDao
import com.example.room_migrations.data.local.database.SampleDatabase
import com.example.room_migrations.data.local.entity.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SampleDatabaseTest {

    private lateinit var userDao: UserDao
    private lateinit var db: SampleDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SampleDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetUserByName() = runBlocking {
        userDao.insertUser(UserEntity(0, "Arthur", "Aguiar"))
        userDao.insertUser(UserEntity(0, "Arthur", "Lopes"))
        val user = userDao.getUserByName("Arthur")
        assert(user != null)
    }

    @Test
    fun deleteAllByLastName() = runBlocking {
        userDao.insertUser(UserEntity(0, "Arthur", "Aguiar"))
        userDao.insertUser(UserEntity(0, "Arthur", "Lopes"))
        userDao.insertUser(UserEntity(0, "Carlos", "Aguiar"))
        val deleteCount = userDao.deleteAllByLastName("Aguiar")
        val user = userDao.getUserByName("Arthur")
        assert(user!!.lastName == "Lopes")
        assert(deleteCount == 2)
    }
}