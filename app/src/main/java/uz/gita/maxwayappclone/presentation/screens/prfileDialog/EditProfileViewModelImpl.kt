package uz.gita.maxwayappclone.presentation.screens.prfileDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.domain.usecase.EditProfileDialogUseCase

class EditProfileViewModelImpl(private val profileUseCase: EditProfileDialogUseCase): ViewModel(),
    EditProfileViewModel {

    override val updateInfoLoadingLiveData = MutableLiveData<Boolean>()

    override val updateInfoSuccessLiveData = MutableLiveData<String>()


    override val updateInfoErrorMessageLiveData = MutableLiveData<String>()
    override val isSuccess = MutableLiveData<Boolean>()


    override fun updateProfileInfo(token: String, name: String, birthDate: String) {
        profileUseCase(token = token,name = name,birthDate = birthDate)
            .onStart { updateInfoLoadingLiveData.value = true }
            .onCompletion { updateInfoLoadingLiveData.value = false }
            .onEach { result ->
                result.onSuccess {
                    isSuccess.value = true
                    it.also { updateInfoSuccessLiveData.value = it }
                }
                result.onFailure { updateInfoErrorMessageLiveData.value = it.message?: "Unknown exception" }
            }
            .launchIn(viewModelScope)
    }
}