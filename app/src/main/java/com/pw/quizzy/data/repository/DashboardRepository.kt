package com.pw.quizzy.data.repository

import com.pw.quizzy.data.model.StudentDashboard
import com.pw.quizzy.data.remote.RetrofitInstance

class DashboardRepository {
    suspend fun getStudentDashboard(): Result<StudentDashboard> {
        return try {
            val response = RetrofitInstance.api.getStudentDashboard()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}