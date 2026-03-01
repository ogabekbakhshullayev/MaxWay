package uz.gita.maxwayappclone.data.source.remote.response

data class ProductByCategoryResponse (
    val id:Long,
    val name:String,
    val products: Array<ProductResponse>
)