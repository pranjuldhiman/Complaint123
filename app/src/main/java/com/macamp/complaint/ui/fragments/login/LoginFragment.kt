package com.macamp.complaint.ui.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.macamp.complaint.data.api.Status
import com.macamp.complaint.databinding.LoginFragmentBinding
import com.macamp.complaint.ui.activities.MainActivity
import com.macamp.complaint.ui.fragments.BaseFragment
import com.macamp.complaint.utils.*

class LoginFragment : BaseFragment() {
    private lateinit var binding: LoginFragmentBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDeviceToken()
        onClickEvents()
    }

    private fun onClickEvents() {
        binding.loginBtn.setOnClickListener {
            val email = binding.usernameET.text.toString()
            val passwordStr = binding.passwordET.text.toString()

            when {
                binding.usernameET.text.toString().isEmpty() -> {
                    toast("Please enter your email address")
                    return@setOnClickListener
                }
                !binding.usernameET.isEmailValid() -> {
                    toast("Please check Email address!")
                    return@setOnClickListener
                }
                passwordStr.isEmpty() -> {
                    toast("Please enter your password!")
                    return@setOnClickListener
                }
                else -> {
                    loginUser(email, passwordStr)
                }
            }
        }
    }

    private fun sendDeviceToken(userId: String) {
        viewModel.deviceToken(
            userId = userId,
            fcmToken = Preferences.prefs?.getString(Constants.FCM_TOKEN, "").toString()
        ).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                    hideProgress()
                    it.data?.let { response ->
                        when (response.code()) {
                            404 -> {

                            }
                            else -> {
                                requireActivity().toast("Successfully LoggedIn!")

                                requireActivity().startActivity<MainActivity>()
                                requireActivity().finishAffinity()

                            }
                        }

                    }

                }
                Status.ERROR -> {
                    hideProgress()

                }

            }

        }
    }

    private fun loginUser(email: String, passwordStr: String) {
        viewModel.login(email, passwordStr).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                    it.data?.let { response ->
                        when (response.code()) {
                            404 -> {
                                toast(response.body()?.message?.get(0).toString())
                            }
                            else -> {
                                val json = Gson().toJson(response.body()?.user)
                                Preferences.prefs?.saveValue(Constants.USER_DATA, json)
                                sendDeviceToken(response.body()?.user?.id.toString())

                            }
                        }

                    }

                }
                Status.ERROR -> {
                    hideProgress()

                }

            }
        }
    }

    private fun getDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Preferences.prefs?.saveValue(Constants.FCM_TOKEN, token)
        })
    }
}

