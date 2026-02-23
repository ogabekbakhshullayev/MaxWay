package uz.gita.maxwayappclone.data.source.remote.response

data class SearchResponse (
    val id: Int,
    val categoryID: Int,
    val name: String,
    val description: String,
    val image: String,
    val cost: Int
)