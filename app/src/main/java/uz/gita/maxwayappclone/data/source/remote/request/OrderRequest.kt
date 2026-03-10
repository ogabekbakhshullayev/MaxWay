package uz.gita.maxwayappclone.data.source.remote.request

import uz.gita.maxwayappclone.data.model.OrderProduct

data class OrderRequest(
    val ls: List<OrderProduct>?,
    val latitude: String,
    val longitude: String,
    val address: String
)
