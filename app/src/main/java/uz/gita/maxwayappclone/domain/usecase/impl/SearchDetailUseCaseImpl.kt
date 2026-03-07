package uz.gita.maxwayappclone.domain.usecase.impl

import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.SearchDetailUseCase

class SearchDetailUseCaseImpl(private val repository: ProductRepository): SearchDetailUseCase {
    override fun invoke(id: Long): ProductUIData? = repository.getItem(id)
}