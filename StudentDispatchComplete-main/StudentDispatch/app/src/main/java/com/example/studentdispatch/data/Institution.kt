package com.example.studentdispatch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "institutions")
data class Institution(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val country: String,
    val city: String,
    val phone: String,
    val website: String,
    val description: String,
    val successfulCases: Int,
    val isSponsored: Boolean,
    val adCost: Int
)
