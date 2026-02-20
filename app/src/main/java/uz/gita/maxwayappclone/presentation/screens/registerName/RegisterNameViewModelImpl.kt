package uz.gita.maxwayappclone.presentation.screens.registerName

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.data.util.checkConnection
import uz.gita.maxwayappclone.domain.usecase.NameDateUseCase
import uz.gita.maxwayappclone.presentation.screens.registerVerify.RegisterVerifyViewModel

class RegisterNameViewModelImpl(private val nameUseCase: NameDateUseCase) : RegisterNameViewModel,
    ViewModel() {

    override val loadingLiveData = MutableLiveData<Boolean>()
    override val successLiveData = MutableLiveData<String>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val noConnectionLiveData = MutableLiveData<Boolean>()


    override fun nameDate(token: String, name: String, date: String) {
        if (checkConnection()) {
            nameUseCase(token, name, date).onStart {
                loadingLiveData.value = true
            }.onCompletion {
                loadingLiveData.value = false
            }.onEach {
                it.onSuccess {
                    successLiveData.value = it
                }.onFailure {
                    errorMessageLiveData.value = it.message ?: "Error"
                }
            }.launchIn(viewModelScope)
        } else {
            noConnectionLiveData.value = true
        }
    }


}