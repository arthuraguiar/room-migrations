package com.example.room_migrations

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.room_migrations.data.local.dao.BankDao
import com.example.room_migrations.data.local.dao.UserDao
import com.example.room_migrations.data.local.database.SampleDatabase
import com.example.room_migrations.data.local.entity.BankEntity
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
    private lateinit var bankDao: BankDao
    private lateinit var db: SampleDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SampleDatabase::class.java).build()
        userDao = db.userDao()
        bankDao = db.bankDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetUserByName() = runBlocking {
        // When
        userDao.insertUser(UserEntity(0, "Arthur", "Aguiar",0.0))
        userDao.insertUser(UserEntity(0, "Arthur", "Lopes", 0.0))
        val user = userDao.getUserByFirstName("Arthur")

        // Then
        assert(user != null)
    }

    @Test
    fun deleteAllByLastName() = runBlocking {
        // When
        userDao.insertUser(UserEntity(0, "Arthur", "Aguiar",0.0))
        userDao.insertUser(UserEntity(0, "Arthur", "Lopes",0.0))
        userDao.insertUser(UserEntity(0, "Carlos", "Aguiar",0.0))
        val deleteCount = userDao.deleteAllByLastName("Aguiar")
        val user = userDao.getUserByFirstName("Arthur")

        // Then
        assert(user!!.lastName == "Lopes")
        assert(deleteCount == 2)
    }

    @Test
    fun insertIntoBankAndGet() = runBlocking {
        // When
        bankDao.insertBank(BankEntity("Banco do Brasil",0, "Rua Mauro Lopes"))
        bankDao.insertBank(BankEntity("Itaú", 0,"Rua 1"))
        val bank1 = bankDao.getBankByName("Banco do Brasil")
        val bank2 = bankDao.getBankByName("Itaú")

        // Then
        assert(bank1 != null)
        assert(bank2 != null)
    }

    @Test
    fun insertIntoBankAndDelete() = runBlocking {
        // When
        bankDao.insertBank(BankEntity( "Banco do Brasil", 0,"Rua Mauro Lopes"))
        bankDao.deleteBankByName("Banco do Brasil")
        val bank1 = bankDao.getBankByName("Banco do Brasil")

        // Then
        assert(bank1 == null)
    }
}