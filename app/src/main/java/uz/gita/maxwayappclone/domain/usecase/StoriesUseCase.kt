package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.source.remote.response.StoryData

interface StoriesUseCase {
    operator fun invoke(): Flow<Result<Array<StoryData>>>
}