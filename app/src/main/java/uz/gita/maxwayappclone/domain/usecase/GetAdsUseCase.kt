package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.domain.model.Ad

interface GetAdsUseCase {
    operator fun invoke(): Flow<Result<List<Ad>>>
}
