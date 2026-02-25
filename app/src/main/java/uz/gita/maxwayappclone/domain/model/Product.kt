package uz.gita.maxwayappclone.domain.model

data class Product(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val description: String,
    val image: String,
    val cost: Long
)
