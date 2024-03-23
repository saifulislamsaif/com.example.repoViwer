package com.example.repoViwer.session


import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreference @Inject constructor(@ApplicationContext context: Context) {

    private var prefName = "AppPreference"
    private var appLanguage = "APP_LANGUAGE"
    private var firebaseToken = "FIREBASE_TOKEN"
    private var isFirstTime = "IS_FIRST_TIME"

    private var pref: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    fun clearAppPreferences() {
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

    private fun sIsFirstTime() {
        val editor = pref.edit()
        editor.putBoolean(isFirstTime, false)
        editor.apply()
    }

    fun gIsFirstTime(): Boolean {
        val isFirstTime = pref.getBoolean(isFirstTime, true)
        sIsFirstTime()
        return isFirstTime
    }

}