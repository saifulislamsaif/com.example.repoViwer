package com.example.repoViwer.utills

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.repoViwer.R
import com.example.repoViwer.utills.ConstantKeys.CLICK_DELAY_MILI_SEC
import com.example.repoViwer.utills.ConstantKeys.F_DATE_DB_SQL
import com.example.repoViwer.utills.ConstantKeys.F_DATE_USER
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun Activity.closeKeyboard() {
    try {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    } catch (_: Exception) {
    }
}

fun Context.haveNetwork(): Boolean {
    val connectivityManager = getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}

fun Activity.popupNoInternet(vRoot: View) {
    Snackbar.make(
        vRoot, getStringCustom(R.string.no_internet) + " " +
                getStringCustom(R.string.turn_on_internet),
        Snackbar.LENGTH_LONG
    ).setAction(getStringCustom(R.string.open_settings)) {
        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }.show()
}

fun getCurrentSqlTime(): String {
    return SimpleDateFormat(F_DATE_DB_SQL, Locale.US).format(Calendar.getInstance().time)
}

fun getCurrentSqlTimeForUser(): String {
    return SimpleDateFormat(F_DATE_USER, Locale.US).format(Calendar.getInstance().time)
}


fun getSqlDateFromUserDate(stringDate: String): String {
    return SimpleDateFormat(
        F_DATE_DB_SQL,
        Locale.US
    ).format(getCalenderFromUserDate(stringDate).time)
}

fun getUserDateFromCalender(calendar: Calendar): String {
    return SimpleDateFormat(F_DATE_USER, Locale.US).format(calendar.time)
}

fun getCalenderFromUserDate(stringDate: String?): Calendar {
    val calendar = Calendar.getInstance()
    try {
        calendar.time = SimpleDateFormat(F_DATE_USER, Locale.US).parse(stringDate)
    } catch (e: ParseException) {
        logPrint(e)
    }
    return calendar
}

fun getCalenderFromSqlDate(stringDate: String): Date {
    var date = Date()
    try {
        date = SimpleDateFormat(F_DATE_USER, Locale.US).parse(stringDate)!!
    } catch (e: ParseException) {
        logPrint(e)
    }
    return date
}

fun getCurrentDdMm(): String {
    return SimpleDateFormat("dd\nMMM", Locale.US).format(Date())
}

fun getChatDate(inputDate: Date?): String {

    if (inputDate == null) return ""
    val newFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    return try {
        newFormat.format(inputDate)
    } catch (e: ParseException) {
        inputDate.toString()
    }
}

fun getChatTime(inputDate: Date?): String {
    if (inputDate == null) return ""
    val newFormat = SimpleDateFormat("hh:mm a", Locale.US)
    return try {
        newFormat.format(inputDate)
    } catch (e: ParseException) {
        inputDate.toString()
    }
}

fun Activity.setToolbarTitle(title: String) {
    try {
        (this as AppCompatActivity).supportActionBar!!.title = title
    } catch (_: Exception) {
    }
}

fun Context.getDrawableImg(name: String): Drawable? {
    val resources = this.resources
    val resourceId = resources.getIdentifier(
        name, "drawable",
        this.packageName
    )

    return try {
        ContextCompat.getDrawable(this, resourceId)
    } catch (e: java.lang.Exception) {
        ContextCompat.getDrawable(this, R.drawable.ic_placeholder)
    }
}

fun logPrint(any: Any?) {
//    if (BuildConfig.DEBUG)
    Log.d("010101", "\n\nLogData = " + Gson().toJson(any) + "\n\n\n")
}

fun Context.toastShow(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.getStringCustom(stringId: Int): String {
    return try {
        this.resources.getString(stringId)
    } catch (_: java.lang.Exception) {
        ""
    }
}

fun getRandomInt(min: Int, max: Int): Int {
    return Random().nextInt((max - min) + 1) + min
}

fun etStringToLong(stNumber: String): Long {
    stNumber.replace(" ", "")
    return try {
        stNumber.toLong()
    } catch (e: Exception) {
        0L
    }
}

fun etStringToDouble(stNumber: String): Double {
    stNumber.replace(" ", "")
    return try {
        stNumber.toDouble()
    } catch (e: Exception) {
        0.0
    }
}

fun isValidLongNumber(stNumber: String): Boolean {
    return !(stNumber.contains(".")
            || stNumber.contains(",")
            || stNumber.contains("+")
            || stNumber.contains("-")
            || stNumber.contains("*")
            || stNumber.contains("/"))
}

fun isValidDoubleNumber(stNumber: String): Boolean {
    stNumber.replace(" ", "")

    return !(stNumber.contains(",")
            || stNumber.contains("+")
            || stNumber.contains("-")
            || stNumber.contains("*")
            || stNumber.contains("/"))
}

fun getOneDigitDecimal(stNumber: Any?): String {
    if (stNumber == null) return "0.0"
    val number = try {
        stNumber.toString().toDouble()
    } catch (e: Exception) {
        0.0
    }
    return String.format("%.1f", number)
}

fun Activity.delayBetweenClick(view: View, delayTime: Long = CLICK_DELAY_MILI_SEC) {
    view.isEnabled = false
    try {
        GlobalScope.launch {
            delay(delayTime)
            runOnUiThread {
                view.isEnabled = true
            }
        }
    } catch (_: java.lang.Exception) {
        view.isEnabled = true
    }
}

fun getGsonString(any: Any?): String {
    return Gson().toJson(any)
}

fun Context.shareText(text: String?) {
    if (text != null) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(shareIntent, getStringCustom(R.string.send_to)))
    }
}

fun Context.copyToClipBoard(text: String?) {
    if (text != null) {
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }
}

fun Context.openWebView(url: String?) {
    if (url != null && url != "") {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}

fun Context.allowNotification() {
    val pagkageName = packageName
    val applicationInfo = applicationInfo
    val intent = Intent()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, pagkageName)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        intent.putExtra("app_package", pagkageName)
        intent.putExtra("app_uid", applicationInfo.uid)
    } else {
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse(pagkageName)
    }

    startActivity(intent)
}

fun Context.shareMyApp() {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app!")
    shareIntent.putExtra(
        Intent.EXTRA_TEXT,
        "Download the amazing MyApp: https://play.google.com/store/apps/details?id=com.example.myapp"
    )
    startActivity(Intent.createChooser(shareIntent, "Share via"))
}

fun Context.contactSupport() {

    val emailsubject: String = "Support"
    val emailbody: String = "BJKSCbgasdkljbclsdkcv ls dhvlsd v"
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject)
    intent.putExtra(Intent.EXTRA_TEXT, emailbody)
    intent.type = "message/rfc822"

    startActivity(Intent.createChooser(intent, "Choose an Email client :"))
}




