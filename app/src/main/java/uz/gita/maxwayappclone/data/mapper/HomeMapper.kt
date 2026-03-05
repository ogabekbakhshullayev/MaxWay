package uz.gita.maxwayappclone.data.mapper

import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.data.source.remote.response.AdResponse
import uz.gita.maxwayappclone.data.source.remote.response.CategoryResponse
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse
import uz.gita.maxwayappclone.domain.model.Ad
import uz.gita.maxwayappclone.domain.model.Category

fun AdResponse.toDomain(): Ad = Ad(id = id, bannerUrl = bannerUrl)
fun CategoryResponse.toDomain(): Category = Category(id = id, name = name)
fun ProductResponse.toDomain(): ProductUIData = ProductUIData(
    id = id,
    categoryId = categoryID,
    name = name,
    description = description,
    image = image,
    cost = cost,
    count = 0
)

fun List<AdResponse>.toAdList(): List<Ad> = map { it.toDomain() }
fun List<CategoryResponse>.toCategoryList(): List<Category> = map { it.toDomain() }
fun List<ProductResponse>.toProductList(): List<ProductUIData> = map { it.toDomain() }
