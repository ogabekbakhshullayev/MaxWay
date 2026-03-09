package uz.gita.maxwayappclone.presentation.screens.basket

import androidx.lifecycle.LiveData
import uz.gita.maxwayappclone.data.model.ProductUIData

interface BasketViewModel {

    val basketItemsLiveData: LiveData<List<ProductUIData>>
    val totalLiveData: LiveData<Long>
    val emptyLiveData: LiveData<Boolean>


    fun load()
    fun clearBasket()
}