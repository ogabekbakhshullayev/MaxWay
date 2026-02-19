package uz.gita.maxwayappclone.presentation.screens.registerVerify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.domain.usecase.VerifyUseCase

class RegisterVerifyViewModelImpl(private val verifyUseCase: VerifyUseCase) :
    RegisterVerifyViewModel,
    ViewModel() {
    override val loadingLiveData = MutableLiveData<Boolean>()
    override val successLiveData = MutableLiveData<String>()
    override val errorMessageLiveData = MutableLiveData<String>()

    override fun verify(phone: String, code: Int) {
        verifyUseCase(phone, code).onStart { loadingLiveData.value = true }
            .onCompletion { loadingLiveData.value = false }.onEach {
                it.onSuccess {
                    successLiveData.value = it
                }.onFailure {
                    errorMessageLiveData.value = it.message ?: "Error"
                }
            }.launchIn(viewModelScope)
    }
}