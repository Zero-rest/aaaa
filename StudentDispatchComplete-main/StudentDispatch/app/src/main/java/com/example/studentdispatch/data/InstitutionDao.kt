package com.example.studentdispatch.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InstitutionDao {
    @Query("""
        SELECT * FROM institutions
        ORDER BY 
            isSponsored DESC,
            adCost DESC,
            successfulCases DESC,
            id DESC
    """)
    fun observeAllSorted(): Flow<List<Institution>>

    @Query("SELECT * FROM institutions WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Institution?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: Institution)

    @Delete
    suspend fun delete(item: Institution)

    @Query("DELETE FROM institutions")
    suspend fun clearAll()
}
