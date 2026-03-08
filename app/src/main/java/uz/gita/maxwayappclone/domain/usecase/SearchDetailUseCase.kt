package uz.gita.maxwayappclone.domain.usecase

import uz.gita.maxwayappclone.data.model.ProductUIData
@Deprecated("do not use")
interface SearchDetailUseCase {
    operator fun invoke(id: Long): ProductUIData?
}