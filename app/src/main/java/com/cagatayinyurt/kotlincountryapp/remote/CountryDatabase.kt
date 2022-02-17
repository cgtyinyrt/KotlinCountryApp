package com.cagatayinyurt.kotlincountryapp.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cagatayinyurt.kotlincountryapp.model.Country

@Database(entities = [Country::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    //Singleton prevents multiple instances of database opening at the same time.
    companion object {
        @Volatile
        private var instance: CountryDatabase? = null

        private var lock = Any()

        // If the instance is not null, then return it, if it is, then create the database.
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CountryDatabase::class.java,
            "countrydatabase"
        ).build()
    }
}