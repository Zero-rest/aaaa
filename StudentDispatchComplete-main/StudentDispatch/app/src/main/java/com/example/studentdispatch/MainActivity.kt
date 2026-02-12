package com.example.studentdispatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.studentdispatch.ui.DispatchApp
import com.example.studentdispatch.ui.DispatchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: DispatchViewModel = viewModel()
            val nav = rememberNavController()
            DispatchApp(nav, vm)
        }
    }
}
