package uz.gita.maxwayappclone.presentation.screens.storiesScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.maxwayappclone.data.source.remote.response.StoryData
import uz.gita.maxwayappclone.domain.usecase.StoriesUseCase

class StoriesViewModelImpl(private val storiesUseCase: StoriesUseCase) : StoriesViewModel,
    ViewModel() {
    override val storiesLiveData = MutableLiveData<Array<StoryData>>()

    override fun getStories() {
        storiesUseCase().onEach {
            it.onSuccess {
                storiesLiveData.value = it
            }.onFailure {

            }
        }.launchIn(viewModelScope)
    }
}