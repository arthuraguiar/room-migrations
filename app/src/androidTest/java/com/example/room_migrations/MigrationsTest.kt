package com.example.room_migrations

import android.util.Log
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.room_migrations.data.local.database.SampleDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

private const val TEST_DB = "migration-test"

@RunWith(AndroidJUnit4::class)
class MigrationsTest {

    private lateinit var db: SupportSQLiteDatabase

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        SampleDatabase::class.java
    )

    @Before
    fun setUp() {
        db = helper.createDatabase(TEST_DB, 1)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun createAndValidateUserId() {
        // Populate first version
        db.execSQL("INSERT INTO user (first_name, last_name) VALUES ('Arthur', 'Aguiar')")
        db.execSQL("INSERT INTO user (first_name, last_name) VALUES ('Arthur', 'Lopes')")

        // Validate the migration
        val cursor = db.query("SELECT * FROM user ORDER by id")
        var expectedId = 1
        while (cursor.moveToNext()) {
            val userId = cursor.getString(cursor.getColumnIndexOrThrow("id"))

            // Perform your assertions on newValue
            Log.d("MigrationTest", "Value of user id column: $userId")
            assert(userId.toInt() == expectedId)
            expectedId = expectedId.inc()
        }
    }

    @Test
    @Throws(IOException::class)
    fun migrate1To2AndValidateBankTable() {
        // Migrate to second version: Validate if each user has now the balance column with the default value of 0.0
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true)

        // Validate the migration
        db.query("INSERT INTO bank (bank_name, address) VALUES ('Banco do Brasil', 'Rua 2')")
        val cursor = db.query("SELECT * FROM bank")
        while (cursor.moveToNext()) {
            val bankName = cursor.getString(cursor.getColumnIndexOrThrow("bank_name"))

            // Perform your assertions on newValue
            Log.d("MigrationTest", "Value of name column: $bankName")
            assert(bankName == "Banco do Brasil")
        }
    }

    @Test
    @Throws(IOException::class)
    fun migrate1To3AndValidateBankId() {
        // Populate first version
        db.execSQL("INSERT INTO user (first_name, last_name) VALUES ('Arthur', 'Aguiar')")
        db.close()

        // Migrate to second version
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true)
        db.query("INSERT INTO bank (bank_name, address) VALUES ('Banco do Brasil', 'Rua 2')")

        // Migrate to third version
        db = helper.runMigrationsAndValidate(TEST_DB, 3, true)

        // Validate the migration: Validate the new column 'id' with generated values and if the 'name' column loose its unique constraint
        var cursor = db.query("SELECT * FROM bank")
        val expectedId = 1
        while (cursor.moveToNext()) {
            val bankId = cursor.getString(cursor.getColumnIndexOrThrow("id"))

            // Perform your assertions on newValue
            Log.d("MigrationTest", "Value of new 'id' column: $bankId")
            assert(bankId == expectedId.toString())
        }

        // Validate the migration: Validate if each user has now the balance column with the default value of 0.0
        cursor = db.query("SELECT * FROM user")
        val expectedBalance = 0.0
        while (cursor.moveToNext()) {
            val userBalance = cursor.getString(cursor.getColumnIndexOrThrow("user_balance"))

            // Perform your assertions on newValue
            Log.d("MigrationTest", "Value of new 'user_balance' column: $userBalance")
            assert(expectedBalance == userBalance.toDouble())
        }
    }
}