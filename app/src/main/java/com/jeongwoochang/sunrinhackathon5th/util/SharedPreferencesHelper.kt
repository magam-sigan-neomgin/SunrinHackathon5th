package com.jeongwoochang.sunrinhackathon5th.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.jeongwoochang.sunrinhackathon5th.R
import timber.log.Timber

class SharedPreferencesHelper(private val context: Context) {
    private val preferences: SharedPreferences

    //Key
    private val AUTO_LOGIN: String
    private val COOKIES: String

    init {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context)
        AUTO_LOGIN = context.resources.getString(R.string.key_auto_login)
        COOKIES = context.resources.getString(R.string.key_cookies)
    }

    //AutoLogin
    var autoLogin: Boolean
        get() = preferences.getBoolean(AUTO_LOGIN, false)
        set(value) {
            val editor = preferences.edit()
            editor.putBoolean(AUTO_LOGIN, value)
            editor.apply()
        }

    //Login-Cookies
    var cookies: HashSet<String>
    get() = preferences.getStringSet(COOKIES, HashSet<String>()) as HashSet<String>
    set(value) {
        val editor = preferences.edit()
        editor.putStringSet(COOKIES, value)
        editor.apply()
    }
    public fun removeCookies() {
        val editor = preferences.edit()
        editor.remove(COOKIES)
        editor.apply()
    }

    fun printAll() {
        val keys = preferences.all
        Timber.d("Printing all shared preferences...")
        if (keys != null) {
            for ((key, value) in keys) {
                Timber.d(
                    key + ": " +
                            value.toString()
                )
            }
            Timber.d("End of all preferences.")
        } else {
            Timber.d("Shared preferences don't exist")
        }
    }

    fun deleteAll() {
        preferences.edit().clear().apply()
        Timber.d("Shared preferences are deleted")
    }
}