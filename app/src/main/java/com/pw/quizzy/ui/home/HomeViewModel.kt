package com.pw.quizzy.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.pw.quizzy.data.model.StudentDashboard
import com.pw.quizzy.data.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = DashboardRepository()

    private val _dashboardState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val dashboardState: StateFlow<DashboardState> = _dashboardState

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            _dashboardState.value = DashboardState.Loading

            val result = repository.getStudentDashboard()
            _dashboardState.value = if (result.isSuccess) {
                DashboardState.Success(result.getOrNull()!!)
            } else {
                DashboardState.Error(result.exceptionOrNull()?.message ?: "Failed to load data")
            }
        }
    }

    fun refreshDashboard() {
        loadDashboard()
    }
}

sealed class DashboardState {
    object Loading : DashboardState()
    data class Success(val data: StudentDashboard) : DashboardState()
    data class Error(val message: String) : DashboardState()
}