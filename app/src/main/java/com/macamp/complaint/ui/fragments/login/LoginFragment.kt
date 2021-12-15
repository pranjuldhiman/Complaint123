package com.macamp.complaint.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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

    private fun loginUser(email: String, passwordStr: String) {
        viewModel.login(email, passwordStr).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                    hideProgress()
                    it.data?.let { response ->
                        when (response.code()) {
                            404 -> {
                                toast(response.body()?.message?.get(0).toString())
                            }
                            else -> {
                                requireActivity().toast("Successfully LoggedIn!")
                                val json = Gson().toJson(response.body()?.user)
                                Preferences.prefs?.saveValue(Constants.USER_DATA, json)
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
}