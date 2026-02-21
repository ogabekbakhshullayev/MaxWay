package uz.gita.maxwayappclone.presentation.screens.branches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.domain.model.Branch
import uz.gita.maxwayappclone.domain.usecase.GetBranchesUseCase

class BranchesViewModel(
    private val getBranchesUseCase: GetBranchesUseCase
) : ViewModel() {

    private val _branchesLiveData = MutableLiveData<List<Branch>>()
    val branchesLiveData: LiveData<List<Branch>> = _branchesLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val _errorMessageLiveData = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> = _errorMessageLiveData

    fun loadBranches() {
        getBranchesUseCase()
            .onStart { _loadingLiveData.value = true }
            .onCompletion { _loadingLiveData.value = false }
            .onEach { result ->
                result.onSuccess { _branchesLiveData.value = it }
                result.onFailure {
                    _errorMessageLiveData.value = it.message ?: "Unknown exception"
                }
            }
            .launchIn(viewModelScope)
    }
}
