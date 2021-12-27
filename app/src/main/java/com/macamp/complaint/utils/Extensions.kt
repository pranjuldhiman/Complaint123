package com.macamp.complaint.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.macamp.complaint.R
import com.macamp.complaint.data.model.Complaints
import com.macamp.complaint.data.model.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}


fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(msg: String) {
    view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show() }
}

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}

fun dataToSingleString(complaints: Complaints, resources: Resources): String {

    return "${resources.getString(R.string.complaint, complaints.complaint)}\n" +
            "${resources.getString(R.string.complaint_id, complaints.id.toString())}\n" +
            "${resources.getString(R.string.name, complaints.name)}\n" +
            "${resources.getString(R.string.mobile, complaints.mobile)}\n" +
            "${resources.getString(R.string.status, complaints.status)}\n" +
            "${resources.getString(R.string.address, complaints.address)}\n" +
            "${resources.getString(R.string.parshad, complaints.parshad)}\n" +
            "${resources.getString(R.string.department, complaints.department)}\n" +
            "${resources.getString(R.string.ward_no, complaints.wardNo)}\n" +
            "-----------------------------\n"
}

fun Activity.setLocale(lang: String?) {

    Preferences.prefs?.saveValue(Constants.APP_LANG, lang)

    val myLocale = Locale(lang)
    val res: Resources = resources
    val dm: DisplayMetrics = res.displayMetrics
    val conf: Configuration = res.configuration
    conf.locale = myLocale
    res.updateConfiguration(conf, dm)
//    val refresh = Intent(this, MainActivity::class.java)
//    finishAffinity()
//    startActivity(refresh)
//    recreate()
}

fun Activity.sendMessage(message: String) {

    // Creating new intent
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.setPackage("com.whatsapp")

    // Give your message here
    intent.putExtra(Intent.EXTRA_TEXT, message)

    // Checking whether Whatsapp is installed or not
    if (intent.resolveActivity(packageManager) == null) {
        toast("Please install whatsapp first.")
        return
    }

    // Starting Whatsapp
    startActivity(intent)
}

fun FragmentActivity.sendMessage(message: String) {

    // Creating new intent
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.setPackage("com.whatsapp")

    // Give your message here
    intent.putExtra(Intent.EXTRA_TEXT, message)

    // Checking whether Whatsapp is installed or not
    if (intent.resolveActivity(packageManager) == null) {
        toast("Please install whatsapp first.")
        return
    }

    // Starting Whatsapp
    startActivity(intent)
}

//get user object in local
fun getUserInfo(): User? {
    val data = Preferences.prefs?.getValue(Constants.USER_DATA, "")
    val gson = Gson()
    return gson.fromJson(data, User::class.java)
}

//get user object in local
fun getCurrentLocalisation(): String? {
    return Preferences.prefs?.getValue(Constants.APP_LANG, "")
}

fun EditText.isEmailValid(): Boolean {
    val emailToText: String = this.text.toString()
    return emailToText.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()
}


fun getPathForCameraImage(context: Activity, bitmap: Bitmap): File {
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