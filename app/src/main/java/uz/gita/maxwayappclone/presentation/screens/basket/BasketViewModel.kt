package uz.gita.maxwayappclone.presentation.screens.basket

import android.R
import androidx.lifecycle.LiveData
import uz.gita.maxwayappclone.data.model.ProductUIData

interface BasketViewModel {

    val basketItemsLiveData: LiveData<List<ProductUIData>>
    val totalLiveData: LiveData<Long>
    val emptyLiveData: LiveData<Boolean>
    val loadingLiveData: LiveData<Boolean>
    val orderSuccessLiveData: LiveData<String>
    val orderErrorMassageLiveData: LiveData<String>


    fun load()
    fun clearBasket()

    fun makeOrder()
}