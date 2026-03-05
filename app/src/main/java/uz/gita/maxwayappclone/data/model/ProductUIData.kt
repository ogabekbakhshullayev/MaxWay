package uz.gita.maxwayappclone.data.model

data class ProductUIData(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val description: String,
    val image: String,
    val cost: Long,
    var count: Int = 0
)

