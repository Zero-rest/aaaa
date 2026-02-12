package com.example.studentdispatch.ui

import com.example.studentdispatch.data.Institution

data class DispatchUiState(
    val all: List<Institution> = emptyList(),
    val query: String = "",
    val country: String = "",
    val city: String = "",
    val isAdmin: Boolean = false
) {
    val countries: List<String> get() = all.map { it.country }.filter { it.isNotBlank() }.distinct().sorted()
    val cities: List<String> get() = all.map { it.city }.filter { it.isNotBlank() }.distinct().sorted()

    val filtered: List<Institution> get() {
        return all.filter { inst ->
            val qOk = query.isBlank() || inst.name.contains(query, true) || inst.description.contains(query, true)
            val cOk = country.isBlank() || inst.country.equals(country, true)
            val cityOk = city.isBlank() || inst.city.equals(city, true)
            qOk && cOk && cityOk
        }
    }
}
