package uz.gita.maxwayappclone.presentation.screens.registerPhone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.domain.usecase.RegisterUseCase

class RegisterPhoneViewModelImpl(private val registerUseCase: RegisterUseCase) : ViewModel(),
    RegisterPhoneViewModel {
    override val loadingLiveData = MutableLiveData<Boolean>()
    override val successLiveData = MutableLiveData<String>()
    override val errorMessageLiveData = MutableLiveData<String>()

    override fun register(phone: String) {
        registerUseCase(phone).onStart { loadingLiveData.value = true }
            .onCompletion { loadingLiveData.value = false }.onEach {
                it.onSuccess {
                    successLiveData.value = it
                }.onFailure {
                    errorMessageLiveData.value = it.message ?: "Error"
                }
            }.launchIn(viewModelScope)
    }
}