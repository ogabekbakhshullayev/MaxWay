package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow

interface VerifyUseCase {
    operator fun invoke(phone: String, code: Int): Flow<Result<String>>
}