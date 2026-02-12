package com.example.studentdispatch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Institution::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun institutionDao(): InstitutionDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "student_dispatch.db"
                ).build()
                INSTANCE = inst
                inst
            }
        }
    }
}
