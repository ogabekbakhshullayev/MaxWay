package uz.gita.maxwayappclone.domain.model

data class Branch(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val openTime: String,
    val closeTime: String,
    val latitude: Double,
    val longitude: Double
)
