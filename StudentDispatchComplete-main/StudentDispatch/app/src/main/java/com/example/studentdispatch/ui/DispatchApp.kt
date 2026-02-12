package com.example.studentdispatch.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun DispatchApp(nav: NavHostController, vm: DispatchViewModel) {
    val state by vm.state.collectAsState()

    NavHost(navController = nav, startDestination = "list") {
        composable("list") {
            DispatchListScreen(
                state = state,
                onQuery = vm::setQuery,
                onCountry = vm::setCountry,
                onCity = vm::setCity,
                onOpenDetail = { id -> nav.navigate("detail/$id") },
                onOpenAdmin = { nav.navigate("admin_login") }
            )
        }
        composable("detail/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")?.toIntOrNull() ?: 0
            DispatchDetailScreen(
                instId = id,
                state = state,
                onBack = { nav.popBackStack() }
            )
        }
        composable("admin_login") {
            AdminLoginScreen(
                onLogin = { pass ->
                    val ok = vm.adminLogin(pass)
                    ok
                },
                onBack = { nav.popBackStack() },
                onGoDashboard = { nav.navigate("admin") }
            )
        }
        composable("admin") {
            AdminDashboardScreen(
                state = state,
                onLogout = {
                    vm.adminLogout()
                    nav.popBackStack("list", false)
                },
                onAdd = { nav.navigate("admin_form/-1") },
                onEdit = { id -> nav.navigate("admin_form/$id") },
                onDelete = { inst -> vm.delete(inst) },
                onReseed = vm::clearAllAndSeed,
                onBack = { nav.popBackStack() }
            )
        }
        composable("admin_form/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")?.toIntOrNull() ?: -1
            AdminInstitutionFormScreen(
                state = state,
                editId = id,
                onSave = { vm.upsert(it); nav.popBackStack() },
                onBack = { nav.popBackStack() }
            )
        }
    }
}
