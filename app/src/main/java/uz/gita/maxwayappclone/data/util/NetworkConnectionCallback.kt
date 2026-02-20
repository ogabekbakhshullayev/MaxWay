package uz.gita.leeson_network.data.mapper

interface NetworkConnectionCallback {
    fun onNetworkAvailable()
    fun onNetworkLost()
    fun onNetworkLosing()
    fun onNetworkUnavailable()
}