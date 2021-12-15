package com.macamp.complaint.ui.fragments.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.macamp.complaint.data.api.Resource
import com.macamp.complaint.data.api.RetrofitClient

class LoginViewModel : ViewModel() {
    val api = RetrofitClient.apiService

    fun login(email: String, password: String) = liveData {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(api.login(email, password)))
        } catch (e: Exception){
            emit(Resource.error(data = api.login(email, password), message = e.localizedMessage?:"Something went Wrong!"))
        }
    }
}