package uz.gita.maxwayappclone.data.source.remote.response

data class ProductResponse(
    val id: Long,
    val categoryID: Long,
    val name: String,
    val description: String,
    val image: String,
    val cost: Long
)