package uz.gita.maxwayappclone.data.source.remote.response

data class ItemProductResponseData(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    val cost: Int
)
