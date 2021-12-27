package com.macamp.complaint.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.macamp.complaint.R
import com.macamp.complaint.data.api.Status
import com.macamp.complaint.data.model.Complaints
import com.macamp.complaint.databinding.ActivityPendingBinding
import com.macamp.complaint.utils.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


@SuppressLint("SetTextI18n")
class PendingActivity : AppCompatActivity() {
    lateinit var binding: ActivityPendingBinding
    var complaints: Complaints? = null
    val viewModel by viewModels<UploadViewModel>()
    private var filePath: String? = ""
    private lateinit var imageView: AppCompatImageView
    private var mProgressDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.showBtnLayout.visibility =
            if (intent.getBooleanExtra("isPending", false)) View.VISIBLE else View.GONE
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        val data = intent.getSerializableExtra("data") as Complaints
        setData(data)
        onClickEvents()
    }

    private fun onClickEvents() {
        binding.doneBtn.setOnClickListener { showDialog() }
        binding.returnBtn.setOnClickListener { showReturnDialog() }
        val shareMessageOnWhatsApp = dataToSingleString(complaints!!, resources)
        binding.shareBtn.setOnClickListener {
            sendMessage(shareMessageOnWhatsApp)
        }


    }

    private fun setData(data: Complaints?) {
        complaints = data
        val convertedDate = Date().getConvertedDate(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd HH:mm:ss",
            data?.createdAt.toString()
        )
        binding.imageViewSelect.visibility =
            if (data?.workDoneImg == null) View.GONE else View.VISIBLE
        binding.tvId.text = getString(R.string.complaint_id, data?.id.toString())
        binding.timeDateTxt.text = convertedDate
        binding.tvComplaint.text = getString(R.string.complaint, data?.complaint.toString())
        binding.tvAddress.text = data?.address
        binding.tvName.text = data?.name
        when (complaints?.status) {
            Constants.WORK_DONE -> {
                binding.statusTxt.text = getString(R.string.resolved)
            }
            Constants.SUBMITTED -> {
                binding.statusTxt.text = getString(R.string.submitted)
            }
            Constants.PENDING -> {
                binding.statusTxt.text = getString(R.string.in_process)

            }
            Constants.RETURN -> {
                binding.statusTxt.text = getString(R.string.returnTxt)
            }
            else -> binding.statusTxt.text = getString(R.string.status, complaints?.complaint)
        }
        binding.ivRatingBar.text = getString(R.string.mobile, data?.mobile.toString())
        binding.ivStar.text = getString(R.string.parshad, data?.parshad.toString())
        binding.ivLike.text = getString(R.string.is_p_r, data?.isPr.toString())
        binding.ivBookmark.text = getString(R.string.ward_no, data?.wardNo.toString())
        binding.tvReason.text = getString(R.string.reason, data?.resean ?: "Not Found!")

    }


    private fun showDialog() {
        val dialog = Dialog(this, R.style.MyDialogTheme)
        dialog.setContentView(R.layout.show_done_dialog)
        imageView = dialog.findViewById(R.id.imageViewSelect)
        val closeBtn: AppCompatImageView = dialog.findViewById(R.id.closeBtn)
        val submitBtn: AppCompatButton = dialog.findViewById(R.id.submitBtn)
        dialog.show()
        imageView.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
        closeBtn.setOnClickListener { dialog.dismiss() }
        submitBtn.setOnClickListener {
            uploadImage(filePath, dialog)
        }


    }

    private fun showReturnDialog() {
        val dialog = Dialog(this, R.style.MyDialogTheme)
        dialog.setContentView(R.layout.show_return_dialog)
        val closeBtn: AppCompatImageView = dialog.findViewById(R.id.closeBtn)
        val submitBtn: AppCompatButton = dialog.findViewById(R.id.submitBtn)
        val returnTxt: AppCompatEditText = dialog.findViewById(R.id.returnTxt)
        dialog.show()

        closeBtn.setOnClickListener { dialog.dismiss() }
        submitBtn.setOnClickListener {
            if (returnTxt.text.toString() == "") {
                toast("Please enter reason to return!")
            } else
                returnBackApiCall(returnTxt.text.toString(), complaints?.id.toString(), dialog)
        }


    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMessageEvent(isRefreshing: String?) {
//        if (isRefreshing == "true")
    }

    private fun returnBackApiCall(reason: String, id: String, dialog: Dialog) {
        viewModel.actionComplaints(id, reason).observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                    hideProgress()
                    sendMessage()

                    it.data?.let { response ->
                        if (response.code() == 200) {
                            toast(response.body()?.string() ?: "Submitted")
                        }
                    }
                    dialog.dismiss()
                    finish()

                }
                Status.ERROR -> {
                    hideProgress()
                }
            }
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                //You can also get File Path from intent
                filePath = UriPathHelper.getPath(this, fileUri)
//                Timber.e(filePath)
                imageView.loadImageWithoutShimmer(filePath ?: "")
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                toast(ImagePicker.getError(data))
            } else {
                toast("Task Cancelled")
            }
        }


    private fun uploadImage(filePath: String?, dialog: Dialog) {
        viewModel.uploadImage(filePath, complaints?.id.toString()).observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                    hideProgress()
                    sendMessage()
                    it.data?.let { response ->
                        if (response.code() == 200) {
                            toast(response.body()?.string() ?: "Submitted for approval")
                        }
                    }
                    dialog.dismiss()
                    finish()

                }
                Status.ERROR -> {
                    hideProgress()
                }
            }
        }
    }

    // Send an Intent with an action named "custom-event-name". The Intent sent should
    // be received by the ReceiverActivity.
    private fun sendMessage() {
        Log.d("sender", "Broadcasting message")
        val intent = Intent("custom-event-name")
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!")
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    fun showProgress() {

        if (mProgressDialog == null) {
            this.let {
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
}