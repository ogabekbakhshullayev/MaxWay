package uz.gita.maxwayappclone.presentation.screens.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.data.source.local.TokenManager
import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse
import uz.gita.maxwayappclone.domain.usecase.ProfileUseCase

class ProfileViewModelImpl(private val editeProfileDialogUseCase: ProfileUseCase) : ViewModel(),
    ProfileViewModel {

    override val getInfoErrorMessageLiveData = MutableLiveData<String>()
    override val getInfoSuccessLiveData = MutableLiveData<EditProfileResponse>()

    override val getInfoLoadingLiveData = MutableLiveData<Boolean>()

    override var userResponse: EditProfileResponse? = null

    override var dateLiveData = MutableLiveData<String>()
    override var nameLiveData = MutableLiveData<String>()


    init {
        getProfileInfo()
    }


    override fun getProfileInfo() {

        Log.d("TTT", "getProfileInfo: ${TokenManager.token}")
        editeProfileDialogUseCase()
            .onStart { getInfoLoadingLiveData.value = true }
            .onCompletion { getInfoLoadingLiveData.value = false }
            .onEach { result ->
                result.onSuccess {
                    getInfoSuccessLiveData.value = it
                }
                result.onFailure {
                    getInfoErrorMessageLiveData.value = it.message ?: "Unknown exception"
                }
            }
            .launchIn(viewModelScope)
    }
}