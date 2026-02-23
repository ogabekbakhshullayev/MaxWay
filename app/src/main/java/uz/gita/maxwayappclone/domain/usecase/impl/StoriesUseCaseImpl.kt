package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.data.source.remote.response.StoryData
import uz.gita.maxwayappclone.domain.repository.StoryRepository
import uz.gita.maxwayappclone.domain.usecase.StoriesUseCase

class StoriesUseCaseImpl(private val repository: StoryRepository): StoriesUseCase {
    override fun invoke(): Flow<Result<Array<StoryData>>> = flow{
        emit(repository.getStories())
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}