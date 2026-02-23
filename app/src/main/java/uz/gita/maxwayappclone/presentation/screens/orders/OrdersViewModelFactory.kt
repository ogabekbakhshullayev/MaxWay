package uz.gita.maxwayappclone.presentation.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.OrdersRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.OrdersUseCaseImpl

class OrdersViewModelFactory: ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return OrdersViewModel(OrdersUseCaseImpl(OrdersRepositoryImpl.getInstance())) as T
	}
}