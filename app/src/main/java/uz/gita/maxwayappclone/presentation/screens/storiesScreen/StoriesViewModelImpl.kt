package uz.gita.maxwayappclone.presentation.screens.storiesScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.maxwayappclone.data.source.remote.response.StoryData
import uz.gita.maxwayappclone.domain.usecase.StoriesUseCase

class StoriesViewModelImpl(private val storiesUseCase: StoriesUseCase) : StoriesViewModel, ViewModel() {
    override val storiesLiveData = MutableLiveData<Array<StoryData>>()
    override val timerLiveData = MutableLiveData<Int>()
    private val maxTimer = 30
    private var currentTime = 0

    fun startTimer() {
        viewModelScope.launch {
            while (currentTime < maxTimer) {
                delay(1000)
                timerLiveData.value = ++ currentTime
            }
        }
    }


    override fun getStories() {
        storiesUseCase().onEach {
            it.onSuccess {
                storiesLiveData.value = it
            }.onFailure {

            }
        }.launchIn(viewModelScope)
    }
}