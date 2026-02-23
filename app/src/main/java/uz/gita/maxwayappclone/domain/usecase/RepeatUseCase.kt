package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow

interface RepeatUseCase {
    operator fun invoke(phone:String): Flow<Result<String>>
}