package uz.gita.maxwayappclone.data.model

data class BranchDto(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val openTime: String,
    val closeTime: String,
    val latitude: Double,
    val longitude: Double
)