package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow

interface NameDateUseCase {
    operator fun invoke(token:String,name:String,date:String): Flow<Result<String>>
}