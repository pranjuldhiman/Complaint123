package com.macamp.complaint.ui.fragments.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.macamp.complaint.data.api.Resource
import com.macamp.complaint.data.api.RetrofitClient

class ComplaintsViewModel : ViewModel() {
    val api by lazy { RetrofitClient.apiService }

    fun getAllComplaints(userId: String) = liveData {
        emit(Resource.loading(data = null))
        try {
            val url = "cs_all_complaints/$userId"
            emit(Resource.success(data = api.getAllComplaints(url)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.localizedMessage))
        }
    }


    fun getReturnBackComplaints(userId: String) = liveData {
        emit(Resource.loading(data = null))
        try {
            val url = "cs_return_back/$userId"
            emit(Resource.success(data = api.getReturnBackComplaints(url)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.localizedMessage))
        }
    }

    fun getCompletedComplaints(userId: String) = liveData {
        emit(Resource.loading(data = null))
        try {
            val url = "cs_work_done/$userId"
            emit(Resource.success(data = api.getCompletedComplaints(url)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.localizedMessage))
        }
    }

    fun getSubmittedComplaints(userId: String) = liveData {
        emit(Resource.loading(data = null))
        try {
            val url = "cs_submitted/$userId"
            emit(Resource.success(data = api.getAllSubmittedComplaints(url)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.localizedMessage))
        }
    }

    fun getInProcessComplaints(userId: String) = liveData {
        emit(Resource.loading(data = null))
        try {
            val url = "cs_in_process/$userId"
            emit(Resource.success(data = api.getInProcessComplaints(url)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.localizedMessage))
        }
    }

}