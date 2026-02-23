package uz.gita.maxwayappclone.data.util

import android.util.Log
import uz.gita.leeson_network.data.mapper.NetworkMonitor
import uz.gita.maxwayappclone.app.MyApp

private val networkMonitor by lazy { NetworkMonitor(MyApp.context) }

fun checkConnection(): Boolean {
    when {
        networkMonitor.isWifiConnected() -> Log.d("TTT", "WiFi orqali ulangan")
        networkMonitor.isCellularConnected() -> Log.d("TTT", "Mobil internet orqali ulangan")
        else -> Log.d("TTT", "Ulanish yo'q")
    }

    if (networkMonitor.isConnected()) {
        Log.d("TTT", "Internet bor")
        return true
    } else {
        Log.d("TTT", "Internet yo'q")
        return false
    }
}