package uz.gita.maxwayappclone.data.source.local

import android.content.Context
import androidx.core.content.edit
import uz.gita.maxwayappclone.app.MyApp

// singleton
object TokenManager {
	private val pref = MyApp.context.getSharedPreferences("MaxWayClone", Context.MODE_PRIVATE)

	init {
		pref.edit { putString("TOKEN", "01e48b85b1aa89d71d05688516524607") }
	}

	var token
		get() = pref.getString("TOKEN", "") ?: ""
		set(value) {
			pref.edit { putString("TOKEN", value) }
		}
}