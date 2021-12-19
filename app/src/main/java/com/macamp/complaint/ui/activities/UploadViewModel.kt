package com.macamp.complaint.ui.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.macamp.complaint.data.api.Resource
import com.macamp.complaint.data.api.RetrofitClient
import com.macamp.complaint.utils.getBodyForImage

class UploadViewModel : ViewModel() {
    private val api by lazy { RetrofitClient.apiService }


    fun uploadImage(filePath: String?, complaintsId: String) = liveData {
        emit(Resource.loading(data = null))
        try {
            val url = "cs_work_submit/$complaintsId"
            if (filePath == "") {
                emit(Resource.success(data = api.workDoneProcessWithoutFile(url)))
            } else {
                emit(Resource.success(data = api.workDoneProcess(url, getBodyForImage(filePath))))
            }
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.localizedMessage))
        }

    }

    fun actionComplaints(id: String, reason: String) = liveData {
        emit(Resource.loading(data = null))
        try {
            val url = "cs_action_return_back"
            emit(Resource.success(data = api.actionReturnBack(url, reason, id)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.localizedMessage))
        }
    }
}