package uz.gita.maxwayappclone.data.mapper

import uz.gita.maxwayappclone.data.source.remote.response.AdResponse
import uz.gita.maxwayappclone.data.source.remote.response.CategoryResponse
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse
import uz.gita.maxwayappclone.domain.model.Ad
import uz.gita.maxwayappclone.domain.model.Category
import uz.gita.maxwayappclone.domain.model.Product

fun AdResponse.toDomain(): Ad = Ad(id = id, bannerUrl = bannerUrl)
fun CategoryResponse.toDomain(): Category = Category(id = id, name = name)
fun ProductResponse.toDomain(): Product = Product(
    id = id,
    categoryId = categoryID,
    name = name,
    description = description,
    image = image,
    cost = cost
)

fun List<AdResponse>.toAdList(): List<Ad> = map { it.toDomain() }
fun List<CategoryResponse>.toCategoryList(): List<Category> = map { it.toDomain() }
fun List<ProductResponse>.toProductList(): List<Product> = map { it.toDomain() }
