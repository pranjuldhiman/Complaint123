package com.macamp.complaint.ui.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.macamp.complaint.R


abstract class BaseFragment : Fragment() {
    private var mActivity: FragmentActivity? = null
    private var mProgressDialog: Dialog? = null
    private var mUploadPhotoDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity
    }

    // function for call fragment
    fun callFragment(view: View, fragmentId: Int) {
        view.findNavController().navigate(fragmentId)
    }

    // method overloading
    fun callFragment(view: View, fragmentId: Int, bundle: Bundle) {
        view.findNavController().navigate(fragmentId, bundle)
    }

    // function for call activity
    fun callActivity(context: AppCompatActivity, activity: AppCompatActivity) {
        startActivity(
            Intent(context, activity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        )
        context.finishAffinity()
    }

    fun showProgress() {
        if (mProgressDialog == null) {
            mActivity?.let {
                mProgressDialog = Dialog(it, android.R.style.Theme_Translucent)
                mProgressDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
                mProgressDialog?.setContentView(R.layout.loader_layout)
                mProgressDialog?.setCancelable(false)
            }
        }
        mProgressDialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        mProgressDialog?.window?.statusBarColor = Color.parseColor("#80000000")
        mProgressDialog?.window?.navigationBarColor = Color.parseColor("#80000000")

        mProgressDialog?.show()
    }

    fun hideProgress() {
        if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
        }
    }


    fun hideKeyboard(dialogView: View) {
        val inputMethodManager = activity?.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(dialogView.windowToken, 0)
    }


}