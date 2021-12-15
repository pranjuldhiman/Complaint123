package com.macamp.complaint.ui.fragments.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.macamp.complaint.data.api.Resource
import com.macamp.complaint.data.api.RetrofitClient
import timber.log.Timber

class DashboardViewModel : ViewModel() {
    val api = RetrofitClient.apiService


    fun getDashboardData(userId: String?) = liveData {
        emit(Resource.loading(data = null))
        try {
            val url = "cs_dashboard_data/$userId"
            emit(Resource.success(api.getDashboardData(url)))
        } catch (e: Exception) {
            Timber.e(e)
            emit(
                Resource.error(
                    data = null,
                    e.localizedMessage ?: "There something wrong in request!"
                )
            )
        }
    }

}