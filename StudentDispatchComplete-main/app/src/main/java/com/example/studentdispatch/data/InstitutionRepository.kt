package com.example.studentdispatch.data

import kotlinx.coroutines.flow.Flow

class InstitutionRepository(private val dao: InstitutionDao) {
    fun observeAll(): Flow<List<Institution>> = dao.observeAllSorted()
    suspend fun get(id: Int): Institution? = dao.getById(id)
    suspend fun upsert(item: Institution) = dao.upsert(item)
    suspend fun delete(item: Institution) = dao.delete(item)
    suspend fun clearAll() = dao.clearAll()
}
