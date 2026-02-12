package com.example.studentdispatch.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentdispatch.data.AppDatabase
import com.example.studentdispatch.data.Institution
import com.example.studentdispatch.data.InstitutionRepository
import com.example.studentdispatch.data.Seed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DispatchViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: InstitutionRepository

    private val _state = MutableStateFlow(DispatchUiState())
    val state: StateFlow<DispatchUiState> = _state

    private val adminPassword = "123456" // اگر خواستی بعداً از SharedPreferences/امن‌ترش می‌کنیم

    init {
        val db = AppDatabase.get(app)
        repo = InstitutionRepository(db.institutionDao())

        viewModelScope.launch {
            repo.observeAll().collect { list ->
                _state.update { it.copy(all = list) }
            }
        }

        // اگر دیتابیس خالی بود، Seed کن
        viewModelScope.launch {
            if (repo.observeAll().firstOrNull().isNullOrEmpty()) {
                Seed.sample().forEach { repo.upsert(it) }
            }
        }
    }

    fun setQuery(q: String) = _state.update { it.copy(query = q) }
    fun setCountry(c: String) = _state.update { it.copy(country = c, city = "") }
    fun setCity(c: String) = _state.update { it.copy(city = c) }

    fun adminLogin(pass: String): Boolean {
        val ok = pass == adminPassword
        if (ok) _state.update { it.copy(isAdmin = true) }
        return ok
    }

    fun adminLogout() = _state.update { it.copy(isAdmin = false) }

    fun upsert(inst: Institution) = viewModelScope.launch { repo.upsert(inst) }
    fun delete(inst: Institution) = viewModelScope.launch { repo.delete(inst) }
    fun clearAllAndSeed() = viewModelScope.launch {
        repo.clearAll()
        Seed.sample().forEach { repo.upsert(it) }
    }
}
