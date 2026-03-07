package uz.gita.maxwayappclone.presentation.screens.search_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.maxwayappclone.data.model.ProductUIData

interface SearchDetailViewModel{

    val productLiveData: MutableLiveData<ProductUIData?>


    fun getItem(id: Long)
}