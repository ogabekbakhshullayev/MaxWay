package uz.gita.maxwayappclone.data.source.local

import android.content.Context
import androidx.core.content.edit
import uz.gita.maxwayappclone.app.MyApp

// singleton
object TokenManager {
	private val pref = MyApp.context.getSharedPreferences("MaxWayClone", Context.MODE_PRIVATE)

//	init {
//		pref.edit { putString("TOKEN", "eaaa466db4d401019db33bf5eafe8252") }
//	}

	var token
		get() = pref.getString("TOKEN", "") ?: ""
		set(value) {
			pref.edit { putString("TOKEN", value) }
		}
}