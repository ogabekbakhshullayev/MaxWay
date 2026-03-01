package uz.gita.maxwayappclone.presentation.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse

interface ProfileViewModel {

    val getInfoErrorMessageLiveData: LiveData<String>
    val getInfoSuccessLiveData: MutableLiveData<EditProfileResponse>
    val getInfoLoadingLiveData: LiveData<Boolean>

    var userResponse: EditProfileResponse?

    fun getProfileInfo(token: String)
}