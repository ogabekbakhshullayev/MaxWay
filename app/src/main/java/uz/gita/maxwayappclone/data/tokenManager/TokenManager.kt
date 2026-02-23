package uz.gita.maxwayappclone.data.tokenManager

import android.content.Context
import android.content.SharedPreferences
import uz.gita.maxwayappclone.app.MyApp
import androidx.core.content.edit

class TokenManager {
    private val pref: SharedPreferences by lazy {
        MyApp.instance.getSharedPreferences("Contact", Context.MODE_PRIVATE)
    }

    fun putToken(token: String) {
        pref.edit { putString("TOKEN", token) }
    }

    fun getToken() = pref.getString("TOKEN", "") ?: ""
}