package com.macamp.complaint.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.gson.Gson
import com.macamp.complaint.data.model.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.text.ParseException


inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}


fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(msg: String) {
    Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
}

//get user object in local
fun getUserInfo(): User? {
    val data = Preferences.prefs?.getValue(Constants.USER_DATA, "")
    val gson = Gson()
    return gson.fromJson(data, User::class.java)
}

fun EditText.isEmailValid(): Boolean {
    val emailToText: String = this.text.toString()
    return emailToText.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()
}


fun getPathForCameraImage(context: Activity, bitmap: Bitmap): File? {
    val destination = File(context.cacheDir, System.currentTimeMillis().toString() + ".png")
    val fo: FileOutputStream
    try {
        destination.createNewFile()
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
        fo = FileOutputStream(destination)
        fo.write(bytes.toByteArray())
        fo.flush()
        fo.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return destination
}

fun getBodyForImage(imagePath: String?): MultipartBody.Part {
    val selectedFile = File(imagePath)
    val filePathBody: RequestBody =
        selectedFile.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("work_image", selectedFile.name, filePathBody)
}

fun Date.getConvertedDate(
    currentFormat: String,
    requiredFormat: String,
    dateString: String
): String {
    var result = ""

    val formatterOld = SimpleDateFormat(currentFormat, Locale.getDefault())
    val formatterNew = SimpleDateFormat(requiredFormat, Locale.getDefault())
    var date: Date? = null
    try {
        date = formatterOld.parse(dateString)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    if (date != null) {
        result = formatterNew.format(date)
    }
    return result

}



fun ImageView.loadImageWithoutShimmer(url: String = "") {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.centerRadius = 20f
    circularProgressDrawable.strokeWidth = 2f
    circularProgressDrawable.start()
    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(circularProgressDrawable)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }
        })
        .into(this)
}