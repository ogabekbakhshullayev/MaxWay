package uz.gita.maxwayappclone.data.source.remote.response

data class ProductResponse (
    val id:Int,
    val name:String,
    val products: Array<ItemProductResponseData>
)