package uz.gita.maxwayappclone.presentation.screens.storiesScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.maxwayappclone.data.source.remote.response.StoryData
import uz.gita.maxwayappclone.domain.usecase.StoriesUseCase

class StoriesViewModelImpl(private val storiesUseCase: StoriesUseCase) : StoriesViewModel,
    ViewModel() {
    override val storiesLiveData = MutableLiveData<Array<StoryData>>()
    override val timerLiveData = MutableLiveData<Int>()
    private val maxTimer = 30
    private var currentTime = 0
    private var timerJob: Job? = null

    override fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            flowCountDown().collect {
                Log.d("TTT","$it")
                timerLiveData.value = it
                if (it == maxTimer) {
                    currentTime = 0
                }
            }
        }
    }

    override fun flowCountDown(): Flow<Int> = flow {
        while (currentTime < maxTimer) {
            emit(currentTime)
            delay(1000)
            currentTime++
        }
    }

    override fun endTimer() {
        timerJob?.cancel()
        currentTime = 0
    }


    override fun getStories() {
        storiesUseCase().onEach {
            it.onSuccess {
                storiesLiveData.value = it
            }.onFailure {
                Log.d("TTT","Fail to get Stories!")
            }
        }.launchIn(viewModelScope)
    }
}