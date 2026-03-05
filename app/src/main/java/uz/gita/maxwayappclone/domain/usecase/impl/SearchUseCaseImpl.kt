package uz.gita.maxwayappclone.domain.usecase.impl

import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.SearchUseCase

class SearchUseCaseImpl(private val repository: ProductRepository): SearchUseCase {
    override fun invoke(query: String): List<ProductUIData> = repository.search(query)
}


